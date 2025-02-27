package org.simpleopenbanking.entity;

import jakarta.persistence.*;
import org.simpleopenbanking.enums.CurrencyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "accountIban", nullable = false)
    private String accountIban;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currencyType;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "time", nullable = false)
    private LocalDateTime timestamp;
}
