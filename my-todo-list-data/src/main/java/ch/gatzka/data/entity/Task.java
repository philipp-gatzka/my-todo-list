package ch.gatzka.data.entity;

import ch.gatzka.data.core.AbstractEntity;
import ch.gatzka.data.enumeration.TaskState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.Instant;

@Getter
@Setter
@Entity
@Audited
public class Task extends AbstractEntity {

  @Column(name = "description", nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "task_state", nullable = false)
  private TaskState state = TaskState.OPEN;

  @Column(name = "due_date", nullable = false)
  private Instant dueDate;

  @ManyToOne(optional = false, fetch = jakarta.persistence.FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

}
