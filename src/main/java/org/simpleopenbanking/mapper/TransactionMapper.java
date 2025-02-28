package org.simpleopenbanking.mapper;

import org.mapstruct.*;
import org.simpleopenbanking.dto.PaymentRequestDto;
import org.simpleopenbanking.dto.TransactionDto;
import org.simpleopenbanking.entity.Transaction;
import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)public interface TransactionMapper {
    Transaction toEntity(TransactionDto transactionDto);

    TransactionDto toDto(Transaction transaction);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transaction partialUpdate(TransactionDto transactionDto, @MappingTarget Transaction transaction);

    TransactionDto toEntity(PaymentRequestDto paymentRequestDto, LocalDateTime now);
}