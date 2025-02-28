package org.simpleopenbanking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {
    UAH("UAH"),
    CHF("CHF"),
    USD("USD"),
    EUR("EUR");

    private final String code;
}
