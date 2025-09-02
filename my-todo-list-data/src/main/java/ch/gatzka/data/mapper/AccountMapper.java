package ch.gatzka.data.mapper;


import ch.gatzka.data.dto.AccountDTO;
import ch.gatzka.data.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  AccountDTO toDTO(Account account);

  @Mapping(target = "tasks", ignore = true)
  Account toEntity(AccountDTO accountDTO);

}
