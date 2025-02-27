package org.simpleopenbanking.dto;

import org.simpleopenbanking.enums.CurrencyType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.simpleopenbanking.entity.Transaction}
 */
public record TransactionDto(long Id, long senderAccountIban, long receiverAccountIban, BigDecimal amount, CurrencyType currencyType, LocalDateTime timestamp) implements Serializable {
  }