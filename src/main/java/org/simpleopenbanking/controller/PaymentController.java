package org.simpleopenbanking.controller;

import lombok.AllArgsConstructor;
import org.simpleopenbanking.dto.PaymentRequestDto;
import org.simpleopenbanking.dto.TransactionDto;
import org.simpleopenbanking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {

    private final TransactionService transactionService;

    @PostMapping("/initiate")
    public ResponseEntity<TransactionDto> initiatePayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        TransactionDto transactionDto = transactionService.initiatePayment(paymentRequestDto);
        return ResponseEntity.ok(transactionDto);
    }
}
