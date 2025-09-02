package ch.gatzka.data.service;

import ch.gatzka.data.entity.Account;
import ch.gatzka.data.entity.Task;
import ch.gatzka.data.enumeration.TaskState;
import ch.gatzka.data.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskDataServiceTest {

  private TaskRepository repository;
  private TaskDataService service;
  private Account account;

  @BeforeEach
  void setUp() {
    repository = mock(TaskRepository.class);
    service = new TaskDataService(repository);
    account = new Account();
  }

  @Test
  void existsById_givenId_thenDelegatesToRepository() {
    when(repository.existsById(1L)).thenReturn(true);
    assertTrue(service.existsById(1L));
  }

  @Test
  void findById_givenId_thenDelegatesToRepository() {
    when(repository.findById(2L)).thenReturn(Optional.of(new Task()));
    assertTrue(service.findById(2L).isPresent());
  }

  @Test
  void save_givenEntity_thenDelegatesToRepository() {
    Task t = new Task();
    when(repository.save(t)).thenReturn(t);
    assertSame(t, service.save(t));
  }

  @Test
  void deleteById_givenId_thenDelegatesToRepository() {
    service.deleteById(3L);
    verify(repository).deleteById(3L);
  }

  @Test
  void findAllByAccount_givenAccount_thenDelegates() {
    when(repository.findAllByAccount(account)).thenReturn(List.of(new Task()));
    assertEquals(1, service.findAllByAccount(account).size());
  }

  @Test
  void findByAccountAndId_givenAccountAndId_thenDelegates() {
    when(repository.findByAccountAndId(account, 5L)).thenReturn(Optional.of(new Task()));
    assertTrue(service.findByAccountAndId(account, 5L).isPresent());
  }

  @Test
  void getByAccountAndId_givenAccountAndId_thenDelegates() {
    Task t = new Task();
    when(repository.getByAccountAndId(account, 6L)).thenReturn(t);
    assertSame(t, service.getByAccountAndId(account, 6L));
  }

  @Test
  void existsByAccountAndId_givenAccountAndId_thenDelegates() {
    when(repository.existsByAccountAndId(account, 7L)).thenReturn(true);
    assertTrue(service.existsByAccountAndId(account, 7L));
  }

  @Test
  void deleteByAccountAndId_givenAccountAndId_thenDelegates() {
    service.deleteByAccountAndId(account, 8L);
    verify(repository).deleteByAccountAndId(account, 8L);
  }

  @Test
  void findAllByAccountAndState_givenAccountAndState_thenDelegates() {
    when(repository.findAllByAccountAndState(account, TaskState.DONE)).thenReturn(List.of());
    assertNotNull(service.findAllByAccountAndState(account, TaskState.DONE));
  }
}
