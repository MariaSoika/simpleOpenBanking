package org.simpleopenbanking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurrencyType {
    UAH("UAH"),
    CHF("CHF"),
    USD("USD"),
    EUR("EUR");

    private final String code;
}
