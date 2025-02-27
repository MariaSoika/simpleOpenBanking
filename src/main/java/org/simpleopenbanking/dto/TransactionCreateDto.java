package org.simpleopenbanking.dto;

import org.simpleopenbanking.entity.Transaction;
import org.simpleopenbanking.enums.CurrencyType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link Transaction}
 */
public record TransactionCreateDto(long senderAccountIban, long receiverAccountIban, BigDecimal amount, CurrencyType currencyType,
                                   LocalDateTime timestamp) implements Serializable {
}