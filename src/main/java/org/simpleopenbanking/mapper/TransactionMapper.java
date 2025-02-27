package org.simpleopenbanking.mapper;

import org.mapstruct.*;
import org.simpleopenbanking.dto.TransactionCreateDto;
import org.simpleopenbanking.dto.TransactionDto;
import org.simpleopenbanking.entity.Transaction;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface TransactionMapper {
    Transaction toEntity(TransactionDto transactionDto);

    TransactionDto toDto(Transaction transaction);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Transaction partialUpdate(TransactionDto transactionDto, @MappingTarget Transaction transaction);

    Transaction toEntity(TransactionCreateDto transactionCreateDto);
}