package org.simpleopenbanking.dto;

import org.simpleopenbanking.entity.Transaction;
import org.simpleopenbanking.enums.CurrencyType;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Transaction}
 */
public record TransactionCreateDto(String senderAccountIban, String receiverAccountIban,
                                   Long amount, CurrencyType currencyType) implements Serializable {
}