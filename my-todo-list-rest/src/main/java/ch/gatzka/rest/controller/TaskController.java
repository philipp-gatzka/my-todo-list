package ch.gatzka.rest.controller;

import ch.gatzka.data.dto.TaskDTO;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.enumeration.TaskState;
import ch.gatzka.data.mapper.TaskMapper;
import ch.gatzka.data.service.AccountDataService;
import ch.gatzka.data.service.TaskDataService;
import ch.gatzka.rest.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {

  private final TaskMapper taskMapper;

  private final TaskDataService taskDataService;

  private final AccountDataService accountDataService;

  private final TaskService service;

  @PutMapping(name = "Create task")
  public ResponseEntity<Void> createTask(@RequestBody @Valid TaskDTO body) {
    service.createTask(getAccount(), body);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping(name = "Get all tasks")
  public ResponseEntity<List<TaskDTO>> getAllTasks() {
    return ResponseEntity.ok().body(service.getAllTasks(getAccount()));
  }

  @GetMapping(name = "Get task by id", value = "/{id}")
  public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") Long id) {
    Account account = getAccount();
    if (!taskDataService.existsByAccountAndId(account, id)) {
      throw new EntityNotFoundException("Task with id " + id + " not found");
    }
    return ResponseEntity.ok().body(service.getTaskById(account, id));
  }

  @GetMapping(name = "Get task by state", value = "/name/{state}")
  public ResponseEntity<List<TaskDTO>> getTasksByState(@PathVariable("state") TaskState state) {
    Account account = getAccount();
    return ResponseEntity.ok().body(service.getTasksByState(account, state));
  }

  @PatchMapping(name = "Update task", value = "/{id}")
  public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") Long id, @RequestBody @Valid TaskDTO body) {
    Account account = getAccount();
    if (!taskDataService.existsByAccountAndId(account, id)) {
      throw new EntityNotFoundException("Task with id " + id + " not found");
    }
    return ResponseEntity.ok().body(service.updateTask(account, id, body));
  }

  @DeleteMapping(name = "Delete task", value = "/{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
    Account account = getAccount();
    if (!taskDataService.existsByAccountAndId(account, id)) {
      throw new EntityNotFoundException("Task with id " + id + " not found");
    }
    service.deleteTaskById(account, id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public Account getAccount() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return accountDataService.findByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("Account not found"));
  }
}
