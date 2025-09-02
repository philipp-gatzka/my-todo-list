package ch.gatzka.rest.service;

import ch.gatzka.data.dto.AccountDTO;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.mapper.AccountMapper;
import ch.gatzka.data.service.AccountDataService;
import ch.gatzka.rest.exception.LoginException;
import ch.gatzka.rest.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

  private final AccountDataService accountDataService;

  private final AccountMapper accountMapper;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  public void register(AccountDTO accountDTO) {
    Account account = accountMapper.toEntity(accountDTO);
    account.setEmail(accountDTO.getEmail().trim().toLowerCase());
    account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
    accountDataService.save(account);
  }

  @Transactional(readOnly = true)
  public String login(AccountDTO accountDTO) {
    Account account = accountDataService.getByEmail(accountDTO.getEmail());
    if (!passwordEncoder.matches(accountDTO.getPassword(), account.getPassword())) {
      throw new LoginException("Email or password is incorrect");
    }
    return jwtService.generateToken(accountDTO.getEmail());
  }

}
