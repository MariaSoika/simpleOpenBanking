package org.simpleopenbanking.service;

import lombok.RequiredArgsConstructor;
import org.simpleopenbanking.dto.TransactionCreateDto;
import org.simpleopenbanking.dto.TransactionDto;
import org.simpleopenbanking.entity.Transaction;
import org.simpleopenbanking.enums.CurrencyType;
import org.simpleopenbanking.mapper.TransactionMapper;
import org.simpleopenbanking.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final WebClient webClient = WebClient.create();

    @Cacheable(value = "transactions", key = "#accountIban")
    @Transactional
    public Page<TransactionDto> getLast10Transactions(Long accountIban) {
        Pageable pageable = PageRequest.of(0, 10);
        logger.info("Get last 10 transactions for account with IBAN: {}", accountIban);
        return transactionRepository.findTop10ByAccountIbanOrderByTimestampDesc(accountIban, pageable)
                .map(transactionMapper::toDto);
    }

    @Transactional
    public TransactionCreateDto initiatePayment(long ibanFrom, long ibanTo, BigDecimal amount, CurrencyType currencyType) {
        logger.info("Initiating transaction from: {} to: {}", ibanFrom, ibanTo);

        if (Objects.equals(ibanFrom, ibanTo)) {
            logger.warn("Cannot initiate payment with the same IBANs: {}", ibanFrom);
            return null;
        }

        return webClient.get()
                .uri("/accounts/{accountIban}/balance", ibanFrom)
                .retrieve()
                .bodyToMono(BigDecimal.class)
                .flatMap(balance -> {
                    if (balance.compareTo(amount) < 0) {
                        logger.warn("Insufficient balance for payment from: {}. Available balance: {}", ibanFrom, balance);
                        return Mono.just(null);
                    }

                    TransactionCreateDto transactionDto = transactionMapper.toEntity(ibanFrom, ibanTo, amount, currencyType, LocalDateTime.now());

                    Transaction transaction = transactionMapper.toEntity(transactionDto);
                    transactionRepository.save(transaction);

                    logger.info("Transaction initiated and saved: {}", transactionDto);

                    return Mono.just(transactionDto);
                })
                .doOnError(error -> logger.error("Error initiating payment: {}", error.getMessage())).block();
    }
}

