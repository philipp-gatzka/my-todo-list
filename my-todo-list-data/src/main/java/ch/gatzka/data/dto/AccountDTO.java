package ch.gatzka.data.dto;

import ch.gatzka.data.core.AbstractDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for {@link ch.gatzka.data.entity.Account}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO extends AbstractDTO {

  @NotBlank(message = "Email must not be blank")
  @Email(message = "Email must be a valid email address")
  private String email;

  @NotBlank(message = "Password must not be blank")
  private String password;

}
