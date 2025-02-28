package org.simpleopenbanking.controller;

import lombok.RequiredArgsConstructor;
import org.simpleopenbanking.dto.AccountDto;
import org.simpleopenbanking.dto.TransactionDto;
import org.simpleopenbanking.service.AccountService;
import org.simpleopenbanking.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/{receiverAccountIban}/balance")
    public ResponseEntity<AccountDto> getAccount(@PathVariable String receiverAccountIban) {
        AccountDto account = accountService.getAccountByIBAN(receiverAccountIban);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{receiverAccountIban}/transactions")
    public ResponseEntity<Page<TransactionDto>> getLast10Transactions(@PathVariable String receiverAccountIban) {
        Page<TransactionDto> transactions = transactionService.getLast10Transactions(receiverAccountIban);
        return ResponseEntity.ok(transactions);
    }

}
