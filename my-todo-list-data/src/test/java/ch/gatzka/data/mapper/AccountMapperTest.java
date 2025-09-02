package ch.gatzka.data.mapper;

import ch.gatzka.data.dto.AccountDTO;
import ch.gatzka.data.entity.Account;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

  private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

  @Test
  void toDTO_givenEntity_thenCopiesFields() {
    Account entity = new Account();
    entity.setEmail("user@example.com");
    entity.setPassword("hash");

    AccountDTO dto = mapper.toDTO(entity);

    assertEquals("user@example.com", dto.getEmail());
    assertEquals("hash", dto.getPassword());
  }

  @Test
  void toEntity_givenDto_thenCopiesFields_and_ignoresTasks() {
    AccountDTO dto = new AccountDTO();
    dto.setEmail("user@example.com");
    dto.setPassword("pw");

    Account entity = mapper.toEntity(dto);

    assertEquals("user@example.com", entity.getEmail());
    assertEquals("pw", entity.getPassword());
    assertNotNull(entity.getTasks());
    assertTrue(entity.getTasks().isEmpty(), "tasks should be initialized but ignored in mapping");
  }
}
