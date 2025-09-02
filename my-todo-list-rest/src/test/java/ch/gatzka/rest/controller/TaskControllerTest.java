package ch.gatzka.rest.controller;

import ch.gatzka.data.dto.TaskDTO;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.enumeration.TaskState;
import ch.gatzka.data.service.AccountDataService;
import ch.gatzka.data.service.TaskDataService;
import ch.gatzka.rest.controller.advice.ExceptionHandling;
import ch.gatzka.rest.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerTest {

  private TaskService taskService;
  private TaskDataService taskDataService;
  private AccountDataService accountDataService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    taskService = Mockito.mock(TaskService.class);
    taskDataService = Mockito.mock(TaskDataService.class);
    accountDataService = Mockito.mock(AccountDataService.class);
    TaskController controller = new TaskController(null, taskDataService, accountDataService, taskService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
      .setControllerAdvice(new ExceptionHandling())
      .build();

    // Set an authentication in the security context
    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("user@example.com", "N/A"));

    Account acc = new Account();
    acc.setEmail("user@example.com");
    when(accountDataService.findByEmail("user@example.com")).thenReturn(Optional.of(acc));
  }

  @Test
  void createTask_givenValidBody_thenCreated() throws Exception {
    mockMvc.perform(put("/api/v1/task")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\":\"test\",\"state\":\"OPEN\"}"))
      .andExpect(status().isCreated());

    verify(taskService).createTask(any(Account.class), any(TaskDTO.class));
  }

  @Test
  void getAllTasks_whenCalled_thenReturnsList() throws Exception {
    when(taskService.getAllTasks(any(Account.class))).thenReturn(List.of(new TaskDTO()));

    mockMvc.perform(get("/api/v1/task"))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void getTaskById_givenNonExisting_thenNotFound() throws Exception {
    when(taskDataService.existsByAccountAndId(any(Account.class), eq(5L))).thenReturn(false);

    mockMvc.perform(get("/api/v1/task/5"))
      .andExpect(status().isNotFound());
  }

  @Test
  void getTaskById_givenExisting_thenOk() throws Exception {
    when(taskDataService.existsByAccountAndId(any(Account.class), eq(1L))).thenReturn(true);
    when(taskService.getTaskById(any(Account.class), eq(1L))).thenReturn(new TaskDTO());

    mockMvc.perform(get("/api/v1/task/1"))
      .andExpect(status().isOk());
  }

  @Test
  void getTasksByState_whenCalled_thenOk() throws Exception {
    when(taskService.getTasksByState(any(Account.class), eq(TaskState.DONE))).thenReturn(List.of());

    mockMvc.perform(get("/api/v1/task/name/DONE"))
      .andExpect(status().isOk());
  }

  @Test
  void updateTask_givenNonExisting_thenNotFound() throws Exception {
    when(taskDataService.existsByAccountAndId(any(Account.class), eq(9L))).thenReturn(false);

    mockMvc.perform(patch("/api/v1/task/9")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\":\"x\",\"state\":\"OPEN\"}"))
      .andExpect(status().isNotFound());
  }

  @Test
  void updateTask_givenExisting_thenOk() throws Exception {
    when(taskDataService.existsByAccountAndId(any(Account.class), eq(10L))).thenReturn(true);
    when(taskService.updateTask(any(Account.class), eq(10L), any(TaskDTO.class))).thenReturn(new TaskDTO());

    mockMvc.perform(patch("/api/v1/task/10")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\":\"x\",\"state\":\"OPEN\"}"))
      .andExpect(status().isOk());
  }

  @Test
  void deleteTask_givenNonExisting_thenNotFound() throws Exception {
    when(taskDataService.existsByAccountAndId(any(Account.class), eq(7L))).thenReturn(false);

    mockMvc.perform(delete("/api/v1/task/7"))
      .andExpect(status().isNotFound());
  }

  @Test
  void deleteTask_givenExisting_thenNoContent() throws Exception {
    when(taskDataService.existsByAccountAndId(any(Account.class), eq(8L))).thenReturn(true);

    mockMvc.perform(delete("/api/v1/task/8"))
      .andExpect(status().isNoContent());

    verify(taskService).deleteTaskById(any(Account.class), eq(8L));
  }
}
