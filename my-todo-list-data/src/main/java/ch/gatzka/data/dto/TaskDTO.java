package ch.gatzka.data.dto;

import ch.gatzka.data.core.AbstractDTO;
import ch.gatzka.data.enumeration.TaskState;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * DTO for {@link ch.gatzka.data.entity.Task}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO extends AbstractDTO {

  @NotBlank(message = "Description must not be blank")
  @Size(max = 255, message = "Description must not exceed 255 characters")
  private String description;

  @Future(message = "Due date must be in the future")
  private Instant dueDate;

  @NotNull(message = "State must not be null")
  private TaskState state = TaskState.OPEN;

}
