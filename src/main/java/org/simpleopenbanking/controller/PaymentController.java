package org.simpleopenbanking.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.simpleopenbanking.dto.TransactionCreateDto;
import org.simpleopenbanking.enums.CurrencyType;
import org.simpleopenbanking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {

    private final TransactionService transactionService;

    @PostMapping("/initiate")
    public ResponseEntity<TransactionCreateDto> initiatePayment(long ibanFrom, long ibanTo, BigDecimal amount, CurrencyType currencyType) {
        TransactionCreateDto transactionCreateDto = transactionService.initiatePayment(ibanFrom, ibanTo, amount, currencyType);
        return ResponseEntity.ok(transactionCreateDto);
    }
}
