package ch.gatzka.data.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDataService<Entity extends AbstractEntity, Repository extends IRepository<Entity>> {

  protected final Repository repository;

  public boolean existsById(Long id) {
    log.debug("{}::existsById(id={})", this.getClass().getSimpleName(), id);
    return repository.existsById(id);
  }

  public Optional<Entity> findById(Long id) {
    log.debug("{}::findById(id={})", this.getClass().getSimpleName(), id);
    return repository.findById(id);
  }

  public Entity save(Entity entity) {
    log.debug("{}::save(entity={})", this.getClass().getSimpleName(), entity);
    return repository.save(entity);
  }

  public void deleteById(Long id) {
    log.debug("{}::deleteById(id={})", this.getClass().getSimpleName(), id);
    repository.deleteById(id);
  }

}
