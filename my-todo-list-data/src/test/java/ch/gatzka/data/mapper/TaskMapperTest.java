package ch.gatzka.data.mapper;

import ch.gatzka.data.dto.TaskDTO;
import ch.gatzka.data.entity.Account;
import ch.gatzka.data.entity.Task;
import ch.gatzka.data.enumeration.TaskState;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

  private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

  @Test
  void toEntity_givenDto_thenMapsFields_and_ignoresAccount() {
    TaskDTO dto = new TaskDTO();
    dto.setDescription("desc");
    dto.setDueDate(Instant.now().plusSeconds(60));
    dto.setState(TaskState.DONE);

    Task entity = mapper.toEntity(dto);

    assertEquals("desc", entity.getDescription());
    assertEquals(dto.getDueDate(), entity.getDueDate());
    assertEquals(TaskState.DONE, entity.getState());
    assertNull(entity.getAccount());
  }

  @Test
  void toDTO_givenEntity_thenMapsAllSimpleFields() {
    Task t = new Task();
    t.setDescription("x");
    t.setDueDate(Instant.now().plusSeconds(120));
    t.setState(TaskState.OPEN);
    t.setAccount(new Account()); // should be ignored when mapping to DTO

    TaskDTO dto = mapper.toDTO(t);

    assertEquals("x", dto.getDescription());
    assertEquals(t.getDueDate(), dto.getDueDate());
    assertEquals(TaskState.OPEN, dto.getState());
  }
}
