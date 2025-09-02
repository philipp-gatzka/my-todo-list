package ch.gatzka.rest.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

  private final JwtService jwtService = new JwtService();

  @Test
  void generateToken_and_validate_roundtrip() {
    String email = "User@Example.com";

    String token = jwtService.generateToken(email);

    // subject should equal the provided email
    assertEquals(email, jwtService.extractUsername(token));
    assertNotNull(jwtService.extractExpiration(token));

    UserDetails userDetails = User.withUsername(email).password("nop").roles("USER").build();

    assertTrue(jwtService.validateToken(token, userDetails));
  }

  @Test
  void validateToken_fails_for_different_username() {
    String token = jwtService.generateToken("a@example.com");
    UserDetails other = User.withUsername("b@example.com").password("nop").roles("USER").build();
    assertFalse(jwtService.validateToken(token, other));
  }
}
