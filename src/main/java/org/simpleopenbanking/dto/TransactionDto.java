package org.simpleopenbanking.dto;

import org.simpleopenbanking.enums.CurrencyType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.simpleopenbanking.entity.Transaction}
 */
public record TransactionDto(long Id, String accountIban, BigDecimal amount, CurrencyType currencyType, String description, LocalDateTime timestamp) implements Serializable {
  }