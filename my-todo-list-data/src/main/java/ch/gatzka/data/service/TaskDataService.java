package ch.gatzka.data.service;

import ch.gatzka.data.core.AbstractDataService;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.entity.Task;
import ch.gatzka.data.enumeration.TaskState;
import ch.gatzka.data.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TaskDataService extends AbstractDataService<Task, TaskRepository> {

  public TaskDataService(TaskRepository repository) {
    super(repository);
  }

  public List<Task> findAllByAccount(Account account) {
    log.debug("{}::findAllByAccount(account={})", this.getClass().getSimpleName(), account);
    return repository.findAllByAccount(account);
  }

  public Optional<Task> findByAccountAndId(Account account, Long id) {
    log.debug("{}::findByAccountAndId(account={}, id={})", this.getClass().getSimpleName(), account, id);
    return repository.findByAccountAndId(account, id);
  }

  public Task getByAccountAndId(Account account, Long id) {
    log.debug("{}::getByAccountAndId(account={}, id={})", this.getClass().getSimpleName(), account, id);
    return repository.getByAccountAndId(account, id);
  }

  public boolean existsByAccountAndId(Account account, Long id) {
    log.debug("{}::existsByAccountAndId(account={}, id={})", this.getClass().getSimpleName(), account, id);
    return repository.existsByAccountAndId(account, id);
  }

  public void deleteByAccountAndId(Account account,Long id) {
    log.debug("{}::deleteByAccountAndId(account={}, id={})", this.getClass().getSimpleName(), account, id);
    repository.deleteByAccountAndId(account, id);
  }

  public List<Task> findAllByAccountAndState(Account account, TaskState state) {
    log.debug("{}::findAllByAccountAndState(account={}, state={})", this.getClass().getSimpleName(), account, state);
    return repository.findAllByAccountAndState(account, state);
  }
}
