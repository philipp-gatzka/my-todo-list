package ch.gatzka.data.core;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@Getter
@ToString
public abstract class AbstractDTO implements Serializable {

  private Long id;

  private Instant createdAt;

  private Instant updatedAt;

}
