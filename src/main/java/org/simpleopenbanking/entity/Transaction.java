package org.simpleopenbanking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.simpleopenbanking.enums.CurrencyType;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "sender_account_iban", nullable = false, length = 29)
    @Pattern(
            regexp = "^UA\\d{2}\\d{6}\\d{19}$",
            message = "IBAN повинен відповідати формату України: UA + 2 цифри + 6 цифр МФО + 19 цифр рахунку"
    )
    private String senderAccountIban;

    @Column(name = "receiver_account_iban", nullable = false, length = 29)
    @Pattern(
            regexp = "^UA\\d{2}\\d{6}\\d{19}$",
            message = "IBAN повинен відповідати формату України: UA + 2 цифри + 6 цифр МФО + 19 цифр рахунку"
    )
    private String receiverAccountIban;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false)
    private CurrencyType currencyType;

    @Column(name = "time", nullable = false)
    private LocalDateTime timestamp;
}
