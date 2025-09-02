package ch.gatzka.rest.security;

import ch.gatzka.data.entity.Account;
import ch.gatzka.data.service.AccountDataService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountDataService accountDataService;

  public UserDetailsServiceImpl(AccountDataService accountDataService) {
    this.accountDataService = accountDataService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountDataService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    return new User(account.getEmail(), account.getPassword(), Collections.emptyList());
  }

}
