package ch.gatzka.rest.service;

import ch.gatzka.data.dto.TaskDTO;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.entity.Task;
import ch.gatzka.data.enumeration.TaskState;
import ch.gatzka.data.mapper.TaskMapper;
import ch.gatzka.data.service.TaskDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

  private final TaskMapper taskMapper;

  private final TaskDataService taskDataService;

  public void createTask(Account account, TaskDTO taskDTO) {
    Task entity = taskMapper.toEntity(taskDTO);
    entity.setAccount(account);
    taskDataService.save(entity);
  }

  @Transactional(readOnly = true)
  public List<TaskDTO> getAllTasks(Account account) {
    return taskDataService.findAllByAccount(account).stream().map(taskMapper::toDTO).toList();
  }

  @Transactional(readOnly = true)
  public List<TaskDTO> getTasksByState(Account account, TaskState state) {
    return taskDataService.findAllByAccountAndState(account, state).stream().map(taskMapper::toDTO).toList();
  }

  @Transactional(readOnly = true)
  public TaskDTO getTaskById(Account account, Long id) {
    return taskMapper.toDTO(taskDataService.getByAccountAndId(account, id));
  }

  public void deleteTaskById(Account account, Long id) {
    taskDataService.deleteByAccountAndId(account, id);
  }

  public TaskDTO updateTask(Account account, Long id, TaskDTO taskDTO) {
    Task task = taskDataService.getByAccountAndId(account, id);
    task.setDueDate(taskDTO.getDueDate());
    task.setState(taskDTO.getState());
    task.setDescription(taskDTO.getDescription());
    Task updatedTask = taskDataService.save(task);
    return taskMapper.toDTO(updatedTask);
  }

}
