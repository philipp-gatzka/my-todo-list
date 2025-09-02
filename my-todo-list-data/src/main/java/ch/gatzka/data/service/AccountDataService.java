package ch.gatzka.data.service;

import ch.gatzka.data.core.AbstractDataService;
import ch.gatzka.data.dto.AccountDTO;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountDataService extends AbstractDataService<Account, AccountRepository> {

  public AccountDataService(AccountRepository repository) {
    super(repository);
  }

  public boolean existsByEmail(String email) {
    return repository.existsByEmailIgnoreCase(email);
  }

  public Optional<Account> findByEmail(String email) {
    return repository.findByEmailIgnoreCase(email);
  }

  public Account getByEmail(String email) {
    return repository.getByEmailIgnoreCase(email);
  }

}
