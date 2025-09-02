package ch.gatzka.rest.service;

import ch.gatzka.data.dto.AccountDTO;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.mapper.AccountMapper;
import ch.gatzka.data.service.AccountDataService;
import ch.gatzka.rest.exception.LoginException;
import ch.gatzka.rest.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock AccountDataService accountDataService;
  @Mock AccountMapper accountMapper;
  @Mock PasswordEncoder passwordEncoder;
  @Mock JwtService jwtService;

  @InjectMocks AuthService authService;

  @Test
  void register_encodesPassword_and_trimsAndLowercasesEmail() {
    AccountDTO dto = new AccountDTO();
    dto.setEmail("  User@Example.COM ");
    dto.setPassword("pw");

    Account mapped = new Account();
    when(accountMapper.toEntity(dto)).thenReturn(mapped);
    when(passwordEncoder.encode("pw")).thenReturn("ENC");

    authService.register(dto);

    assertEquals("user@example.com", mapped.getEmail());
    assertEquals("ENC", mapped.getPassword());
    verify(accountDataService).save(mapped);
  }

  @Test
  void login_returnsJwtOnSuccess() {
    AccountDTO dto = new AccountDTO();
    dto.setEmail("user@example.com");
    dto.setPassword("pw");

    Account account = new Account();
    account.setPassword("HASH");
    when(accountDataService.getByEmail("user@example.com")).thenReturn(account);
    when(passwordEncoder.matches("pw", "HASH")).thenReturn(true);
    when(jwtService.generateToken("user@example.com")).thenReturn("TOKEN");

    String token = authService.login(dto);

    assertEquals("TOKEN", token);
  }

  @Test
  void login_throws_ifWrongPassword() {
    AccountDTO dto = new AccountDTO();
    dto.setEmail("user@example.com");
    dto.setPassword("pw");

    Account account = new Account();
    account.setPassword("HASH");
    when(accountDataService.getByEmail("user@example.com")).thenReturn(account);
    when(passwordEncoder.matches("pw", "HASH")).thenReturn(false);

    assertThrows(LoginException.class, () -> authService.login(dto));
    verify(jwtService, never()).generateToken(anyString());
  }
}
