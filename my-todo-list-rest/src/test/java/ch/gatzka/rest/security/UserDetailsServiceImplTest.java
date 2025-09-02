package ch.gatzka.rest.security;

import ch.gatzka.data.entity.Account;
import ch.gatzka.data.service.AccountDataService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

  @Test
  void loadUserByUsername_givenExisting_thenReturnsUserDetails() {
    AccountDataService dataService = mock(AccountDataService.class);
    Account account = new Account();
    account.setEmail("user@example.com");
    account.setPassword("hash");
    when(dataService.findByEmail("user@example.com")).thenReturn(Optional.of(account));

    UserDetailsServiceImpl impl = new UserDetailsServiceImpl(dataService);

    assertEquals("user@example.com", impl.loadUserByUsername("user@example.com").getUsername());
  }

  @Test
  void loadUserByUsername_givenMissing_thenThrows() {
    AccountDataService dataService = mock(AccountDataService.class);
    when(dataService.findByEmail("missing@example.com")).thenReturn(Optional.empty());

    UserDetailsServiceImpl impl = new UserDetailsServiceImpl(dataService);

    assertThrows(UsernameNotFoundException.class, () -> impl.loadUserByUsername("missing@example.com"));
  }
}
