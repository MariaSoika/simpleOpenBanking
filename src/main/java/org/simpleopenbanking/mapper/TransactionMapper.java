package org.simpleopenbanking.mapper;

import org.mapstruct.*;
import org.simpleopenbanking.dto.TransactionCreateDto;
import org.simpleopenbanking.dto.TransactionDto;
import org.simpleopenbanking.entity.Transaction;
import org.simpleopenbanking.enums.CurrencyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface TransactionMapper {
    Transaction toEntity(TransactionDto transactionDto);

    TransactionDto toDto(Transaction transaction);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)Transaction partialUpdate(TransactionDto transactionDto, @MappingTarget Transaction transaction);

    Transaction toEntity(TransactionCreateDto transactionCreateDto);

    TransactionCreateDto toEntity(long ibanFrom, long ibanTo, BigDecimal amount, CurrencyType currencyType, LocalDateTime now);
}