package ch.gatzka.rest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtFilterTest {

  private JwtService jwtService;
  private UserDetailsService userDetailsService;
  private JwtFilter filter;

  @BeforeEach
  void setUp() {
    jwtService = Mockito.mock(JwtService.class);
    userDetailsService = Mockito.mock(UserDetailsService.class);
    filter = new JwtFilter(jwtService, userDetailsService);
    SecurityContextHolder.clearContext();
  }

  @Test
  void doFilter_givenValidBearerToken_thenSetsAuthentication() throws ServletException, IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain chain = mock(FilterChain.class);

    String token = "tkn";
    when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
    when(jwtService.extractUsername(token)).thenReturn("user@example.com");

    UserDetails details = User.withUsername("user@example.com").password("pw").roles("USER").build();
    when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(details);
    when(jwtService.validateToken(token, details)).thenReturn(true);

    filter.doFilterInternal(request, response, chain);

    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    verify(chain).doFilter(request, response);
  }

  @Test
  void doFilter_givenNoHeader_thenPassesThrough() throws ServletException, IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain chain = mock(FilterChain.class);

    when(request.getHeader("Authorization")).thenReturn(null);

    filter.doFilterInternal(request, response, chain);

    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(chain).doFilter(request, response);
  }
}
