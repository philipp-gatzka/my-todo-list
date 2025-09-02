package ch.gatzka.data.mapper;

import ch.gatzka.data.dto.TaskDTO;
import ch.gatzka.data.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  @Mapping(target = "account", ignore = true)
  Task toEntity(TaskDTO value);

  TaskDTO toDTO(Task value);

  List<Task> toEntities(List<TaskDTO> value);

  List<TaskDTO> toDTOs(List<Task> value);

}
