package org.simpleopenbanking.controller;

import lombok.RequiredArgsConstructor;
import org.simpleopenbanking.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
}
