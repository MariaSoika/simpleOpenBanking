package org.simpleopenbanking.controller;

import lombok.RequiredArgsConstructor;
import org.simpleopenbanking.service.TransactionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
}
