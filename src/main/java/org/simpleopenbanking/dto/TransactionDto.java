package org.simpleopenbanking.dto;

import org.simpleopenbanking.enums.CurrencyType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.simpleopenbanking.entity.Transaction}
 */
public record TransactionDto(Long Id, String senderAccountIban, String receiverAccountIban, Long amount, CurrencyType currencyType, LocalDateTime timestamp) implements Serializable {
  }