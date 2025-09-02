package ch.gatzka.data.repository;

import ch.gatzka.data.core.IRepository;
import ch.gatzka.data.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends IRepository<Account> {

  boolean existsByEmailIgnoreCase(String email);

  Optional<Account> findByEmailIgnoreCase(String email);

  Account getByEmailIgnoreCase(String email);

}
