package org.simpleopenbanking.dto;

import org.simpleopenbanking.entity.Account;
import org.simpleopenbanking.enums.CurrencyType;
import java.io.Serializable;

/**
 * DTO for {@link Account}
 */
public record AccountDto(Long iban, Long balance, CurrencyType currencyType) implements Serializable {
}