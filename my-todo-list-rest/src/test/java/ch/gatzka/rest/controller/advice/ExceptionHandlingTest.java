package ch.gatzka.rest.controller.advice;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlingTest {

  private final ExceptionHandling advice = new ExceptionHandling();

  @Test
  void handleEntityNotFound_givenException_thenReturns404() {
    ResponseEntity<Void> response = advice.handleEntityNotFoundException(new EntityNotFoundException("missing"));
    assertEquals(404, response.getStatusCode().value());
  }

  @Test
  void handleIllegalArgument_givenException_thenReturns400() {
    ResponseEntity<Void> response = advice.handleIllegalArgumentException(new IllegalArgumentException("bad"));
    assertEquals(400, response.getStatusCode().value());
  }

  @Test
  void handleLoginException_givenException_thenReturns401() {
    ResponseEntity<Void> response = advice.handleException(new ch.gatzka.rest.exception.LoginException("no"));
    assertEquals(401, response.getStatusCode().value());
  }
}
