package org.simpleopenbanking.dto;

import org.simpleopenbanking.entity.Account;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Account}
 */
public record AccountDto(long iban, BigDecimal balance) implements Serializable {
}