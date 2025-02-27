package org.simpleopenbanking.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    @Cacheable(value = "accounts", key = "#accountIban")
    @Transactional
    public AccountDto getAccountByIBAN(long accountIban) {
        return accountRepository.findById(accountIban)
                .map(accountMapper::toDto)
                .orElse(null);
    }
}
