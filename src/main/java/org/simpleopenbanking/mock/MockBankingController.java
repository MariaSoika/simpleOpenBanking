package org.simpleopenbanking.mock;

import lombok.AllArgsConstructor;
import org.simpleopenbanking.entity.Transaction;
import org.simpleopenbanking.enums.CurrencyType;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class MockBankingController {
    private final Map<Long, Double> accountBalances = new HashMap<>();
    private final Map<Long, List<Transaction>> transactions = new HashMap<>();

    public MockBankingController() {
        accountBalances.put(89370400440013000L, 1000.00);
        transactions.put(8937040044053201300L, List.of(
                new Transaction(1L,88856876576576504L,89370400440013000L, BigDecimal.valueOf(-100.00), CurrencyType.UAH, LocalDateTime.now()),
                new Transaction(1L,88856876576576504L,89370400440013000L, BigDecimal.valueOf(100.00), CurrencyType.UAH, LocalDateTime.now()),
                new Transaction(1L,88856876576576504L,89370400440013000L, BigDecimal.valueOf(500.00), CurrencyType.UAH, LocalDateTime.now())
        ));
    }
}
