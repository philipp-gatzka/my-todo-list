package ch.gatzka.data.core;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface IRepository<Entity extends AbstractEntity> extends Repository<Entity, Long> {

  boolean existsById(Long id);

  Optional<Entity> findById(Long id);

  Entity save(Entity entity);

  void deleteById(Long id);

}
