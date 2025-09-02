package ch.gatzka.rest.service;

import ch.gatzka.data.dto.TaskDTO;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.entity.Task;
import ch.gatzka.data.enumeration.TaskState;
import ch.gatzka.data.mapper.TaskMapper;
import ch.gatzka.data.service.TaskDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @Mock TaskMapper taskMapper;
  @Mock TaskDataService taskDataService;

  @InjectMocks TaskService taskService;

  @Test
  void createTask_setsAccount_and_saves() {
    Account acc = new Account();
    TaskDTO dto = new TaskDTO();
    Task entity = new Task();

    when(taskMapper.toEntity(dto)).thenReturn(entity);

    taskService.createTask(acc, dto);

    assertEquals(acc, entity.getAccount());
    verify(taskDataService).save(entity);
  }

  @Test
  void getAllTasks_mapsEntitiesToDtos() {
    Account acc = new Account();
    Task entity = new Task();
    TaskDTO dto = new TaskDTO();

    when(taskDataService.findAllByAccount(acc)).thenReturn(List.of(entity));
    when(taskMapper.toDTO(entity)).thenReturn(dto);

    List<TaskDTO> result = taskService.getAllTasks(acc);

    assertEquals(1, result.size());
    assertSame(dto, result.get(0));
  }

  @Test
  void getTasksByState_filtersByState() {
    Account acc = new Account();
    Task entity = new Task();
    TaskDTO dto = new TaskDTO();

    when(taskDataService.findAllByAccountAndState(acc, TaskState.DONE)).thenReturn(List.of(entity));
    when(taskMapper.toDTO(entity)).thenReturn(dto);

    List<TaskDTO> result = taskService.getTasksByState(acc, TaskState.DONE);

    assertEquals(1, result.size());
    assertSame(dto, result.get(0));
  }

  @Test
  void getTaskById_returnsMappedDto() {
    Account acc = new Account();
    Task entity = new Task();
    TaskDTO dto = new TaskDTO();

    when(taskDataService.getByAccountAndId(acc, 1L)).thenReturn(entity);
    when(taskMapper.toDTO(entity)).thenReturn(dto);

    assertSame(dto, taskService.getTaskById(acc, 1L));
  }

  @Test
  void updateTask_updatesFields_andPersists_andReturnsDto() {
    Account acc = new Account();
    Task entity = new Task();
    entity.setDescription("old");
    entity.setState(TaskState.OPEN);
    entity.setDueDate(java.time.Instant.now());

    TaskDTO dto = new TaskDTO();
    dto.setDescription("new");
    dto.setState(TaskState.DONE);
    dto.setDueDate(java.time.Instant.now().plusSeconds(86400));

    when(taskDataService.getByAccountAndId(acc, 5L)).thenReturn(entity);
    when(taskDataService.save(entity)).thenReturn(entity);

    TaskDTO mapped = new TaskDTO();
    when(taskMapper.toDTO(entity)).thenReturn(mapped);

    TaskDTO result = taskService.updateTask(acc, 5L, dto);

    assertEquals("new", entity.getDescription());
    assertEquals(TaskState.DONE, entity.getState());
    assertEquals(dto.getDueDate(), entity.getDueDate());
    assertSame(mapped, result);
  }

  @Test
  void deleteTaskById_delegatesToDataService() {
    Account acc = new Account();
    taskService.deleteTaskById(acc, 9L);
    verify(taskDataService).deleteByAccountAndId(acc, 9L);
  }
}
