package org.simpleopenbanking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.simpleopenbanking.enums.CurrencyType;

import java.math.BigDecimal;
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
    private long Id;

    @Column(name = "sender_account_iban", nullable = false)
    private long senderAccountIban;

    @Column(name = "receiver_account_iban", nullable = false)
    private long receiverAccountIban;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false)
    private CurrencyType currencyType;

    @Column(name = "time", nullable = false)
    private LocalDateTime timestamp;

}
