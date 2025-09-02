package ch.gatzka.rest.controller.advice;

import ch.gatzka.rest.exception.LoginException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Void> handleEntityNotFoundException(EntityNotFoundException exception) {
    return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage())).build();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException exception) {
    return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage())).build();
  }

  @ExceptionHandler(LoginException.class)
  public ResponseEntity<Void> handleException(LoginException exception) {
    return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage())).build();
  }

}
