package ch.gatzka.rest.controller;

import ch.gatzka.data.dto.AccountDTO;
import ch.gatzka.data.service.AccountDataService;
import ch.gatzka.rest.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

  private AccountDataService accountDataService;
  private AuthService authService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    accountDataService = Mockito.mock(AccountDataService.class);
    authService = Mockito.mock(AuthService.class);
    AuthController controller = new AuthController(accountDataService, authService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void register_givenExistingEmail_thenReturnsBadRequest() throws Exception {
    when(accountDataService.existsByEmail("user@example.com")).thenReturn(true);

    mockMvc.perform(put("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\" user@example.com \",\"password\":\"pw\"}"))
      .andExpect(status().isBadRequest());

    verify(authService, never()).register(any(AccountDTO.class));
  }

  @Test
  void register_givenNewEmail_thenCreates() throws Exception {
    when(accountDataService.existsByEmail("user@example.com")).thenReturn(false);

    mockMvc.perform(put("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\" user@example.com \",\"password\":\"pw\"}"))
      .andExpect(status().isCreated());

    verify(authService).register(any(AccountDTO.class));
  }

  @Test
  void login_givenUnknownEmail_thenBadRequest() throws Exception {
    when(accountDataService.existsByEmail("user@example.com")).thenReturn(false);

    mockMvc.perform(get("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\" user@example.com \",\"password\":\"pw\"}"))
      .andExpect(status().isBadRequest());

    verify(authService, never()).login(any(AccountDTO.class));
  }

  @Test
  void login_givenKnownEmail_thenOk() throws Exception {
    when(accountDataService.existsByEmail("user@example.com")).thenReturn(true);
    when(authService.login(any(AccountDTO.class))).thenReturn("TOKEN");

    mockMvc.perform(get("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\" user@example.com \",\"password\":\"pw\"}"))
      .andExpect(status().isOk());

    verify(authService).login(any(AccountDTO.class));
  }
}
