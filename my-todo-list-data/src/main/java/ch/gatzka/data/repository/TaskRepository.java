package ch.gatzka.data.repository;

import ch.gatzka.data.core.IRepository;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.entity.Task;
import ch.gatzka.data.enumeration.TaskState;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends IRepository<Task> {

  List<Task> findAllByAccount(Account account);

  Optional<Task> findByAccountAndId(Account account, Long id);

  boolean existsByAccountAndId(Account account, Long id);

  List<Task> findAllByAccountAndState(Account account, TaskState taskState);

  Task getByAccountAndId(Account account, Long id);

  void deleteByAccountAndId(Account account, Long id);

}
