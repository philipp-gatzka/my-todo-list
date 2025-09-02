package ch.gatzka.rest.controller;

import ch.gatzka.data.dto.AccountDTO;
import ch.gatzka.data.service.AccountDataService;
import ch.gatzka.rest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AccountDataService accountDataService;

  private final AuthService service;

  @PutMapping(name = "Register", value = "/register")
  public ResponseEntity<Void> register(@RequestBody AccountDTO body) {
    if (accountDataService.existsByEmail(body.getEmail().trim())) {
      return ResponseEntity.badRequest().build();
    }
    service.register(body);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping(name = "Login", value = "/login")
  public ResponseEntity<String> login(@RequestBody AccountDTO body) {
    if (!accountDataService.existsByEmail(body.getEmail().trim())) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(service.login(body));
  }

}
