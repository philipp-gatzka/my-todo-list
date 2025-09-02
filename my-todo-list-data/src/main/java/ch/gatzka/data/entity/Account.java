package ch.gatzka.data.entity;

import ch.gatzka.data.core.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Audited
public class Account extends AbstractEntity {

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToMany(mappedBy = "account", orphanRemoval = true, fetch = jakarta.persistence.FetchType.LAZY)
  private Set<Task> tasks = new LinkedHashSet<>();

}
