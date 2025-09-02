package ch.gatzka.data.service;

import ch.gatzka.data.entity.Account;
import ch.gatzka.data.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountDataServiceTest {

  private AccountRepository repository;
  private AccountDataService service;

  @BeforeEach
  void setUp() {
    repository = mock(AccountRepository.class);
    service = new AccountDataService(repository);
  }

  @Test
  void existsById_givenId_thenDelegatesToRepository() {
    when(repository.existsById(1L)).thenReturn(true);
    assertTrue(service.existsById(1L));
  }

  @Test
  void findById_givenId_thenDelegatesToRepository() {
    when(repository.findById(2L)).thenReturn(Optional.of(new Account()));
    assertTrue(service.findById(2L).isPresent());
  }

  @Test
  void save_givenEntity_thenDelegatesToRepository() {
    Account acc = new Account();
    when(repository.save(acc)).thenReturn(acc);
    assertSame(acc, service.save(acc));
  }

  @Test
  void deleteById_givenId_thenDelegatesToRepository() {
    service.deleteById(3L);
    verify(repository).deleteById(3L);
  }

  @Test
  void existsByEmail_givenEmail_thenDelegates() {
    when(repository.existsByEmailIgnoreCase("user@example.com")).thenReturn(true);
    assertTrue(service.existsByEmail("user@example.com"));
  }

  @Test
  void findByEmail_givenEmail_thenDelegates() {
    when(repository.findByEmailIgnoreCase("user@example.com")).thenReturn(Optional.of(new Account()));
    assertTrue(service.findByEmail("user@example.com").isPresent());
  }

  @Test
  void getByEmail_givenEmail_thenDelegates() {
    Account a = new Account();
    when(repository.getByEmailIgnoreCase("user@example.com")).thenReturn(a);
    assertSame(a, service.getByEmail("user@example.com"));
  }
}
