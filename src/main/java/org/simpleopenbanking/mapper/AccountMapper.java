package org.simpleopenbanking.mapper;

import org.mapstruct.*;
import org.simpleopenbanking.entity.Account;
import org.simpleopenbanking.dto.AccountDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    Account toEntity(AccountDto accountDto);

    AccountDto toDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(AccountDto accountDto, @MappingTarget Account account);

    //Account toEntity(AccountCreateDto accountCreateDto);
}