package org.simpleopenbanking.dto;

import org.simpleopenbanking.enums.CurrencyType;

public record PaymentRequestDto(String ibanFrom, String ibanTo,
                                Long amount, CurrencyType currencyType) {
}
