package org.simpleopenbanking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.simpleopenbanking.enums.CurrencyType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "iban", nullable = false, length = 29)
    @Pattern(
            regexp = "^UA\\d{2}\\d{6}\\d{19}$",
            message = "IBAN повинен відповідати формату України: UA + 2 цифри + 6 цифр МФО + 19 цифр рахунку"
    )
    private String iban;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false)
    private CurrencyType currencyType;
}
