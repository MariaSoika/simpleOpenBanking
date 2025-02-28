package org.simpleopenbanking.dto;

import org.simpleopenbanking.enums.CurrencyType;

public record PaymentRequestDto(Long ibanFrom, Long ibanTo,
                                Long amount, CurrencyType currencyType) {
}
