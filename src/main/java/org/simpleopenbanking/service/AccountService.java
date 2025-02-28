package org.simpleopenbanking.service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.simpleopenbanking.dto.AccountDto;
import org.simpleopenbanking.mapper.AccountMapper;
import org.simpleopenbanking.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    @Operation(summary = "get account by IBAN", description = "simple findById()")
    @Cacheable(value = "accounts", key = "#accountIban")
    @Transactional
    public AccountDto getAccountByIBAN(long accountIban) {
        logger.info("getting account by IBAN {}...", accountIban);
        return accountRepository.findById(accountIban)
                .map(accountMapper::toDto)
                .orElseGet(() -> {
                    logger.error("Something went wrong");
                    return null;
                });
    }
}
