package org.simpleopenbanking.controller;

import lombok.AllArgsConstructor;
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

    @GetMapping("/{accountIban}/balance")
    public ResponseEntity<AccountDto> getAccount(@PathVariable long accountIban) {
        AccountDto account = accountService.getAccountByIBAN(accountIban);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountIban}/transactions")
    public ResponseEntity<Page<TransactionDto>> getLast10Transactions(@PathVariable long accountIban) {
        Page<TransactionDto> transactions = transactionService.getLast10Transactions(accountIban);
        return ResponseEntity.ok(transactions);
    }

}
