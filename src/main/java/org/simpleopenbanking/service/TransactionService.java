package org.simpleopenbanking.service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.simpleopenbanking.dto.AccountDto;
import org.simpleopenbanking.dto.PaymentRequestDto;
import org.simpleopenbanking.dto.TransactionDto;
import org.simpleopenbanking.entity.Transaction;
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

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final WebClient webClient = WebClient.create();

    @Operation(summary = "last 10 transaction of receiver", description = "Used custom Sql request")
    @Cacheable(value = "transactions", key = "#accountIban")
    @Transactional
    public Page<TransactionDto> getLast10Transactions(Long accountIban) {
        Pageable pageable = PageRequest.of(0, 10);
        logger.info("Get last 10 transactions for account with IBAN: {}", accountIban);
        return transactionRepository.findTop10ByReceiverAccountIbanOrderByTimestampDesc(accountIban, pageable)
                .map(transactionMapper::toDto);
    }

    @Operation(summary = "Initiate Payment", description = "Create a payment transaction between two accounts, " +
            "using: IBAN of sender, IBAN of receiver, amount, currencyType (enum)")
    @Transactional
    public TransactionDto initiatePayment(PaymentRequestDto paymentRequestDto) {
        logger.info("Initiating transaction from: {} to: {}", paymentRequestDto.ibanFrom(), paymentRequestDto.ibanTo());

        if (Objects.equals(paymentRequestDto.ibanFrom(), paymentRequestDto.ibanTo())) {
            logger.warn("Cannot initiate payment with the same IBANs: {}", paymentRequestDto.ibanFrom());
            throw new IllegalArgumentException("IBANs must be different");
        }

        try {
            Long balance = webClient.get()
                    .uri("http://localhost:8080/api/accounts/{accountIban}/balance", paymentRequestDto.ibanFrom())
                    .retrieve()
                    .bodyToMono(AccountDto.class)
                    .flatMap(response -> Mono.just(response.balance()))
                    .block();

            if (balance == null || balance.compareTo(paymentRequestDto.amount()) < 0) {
                logger.warn("Insufficient balance for payment from: {}. Available balance: {}", paymentRequestDto.ibanFrom(), balance);
                throw new IllegalStateException("Insufficient balance");
            }

            Transaction transaction = transactionMapper.toEntity(paymentRequestDto, LocalDateTime.now());
            transaction.setSenderAccountIban(paymentRequestDto.ibanFrom());
            transaction.setReceiverAccountIban(paymentRequestDto.ibanTo());
            transaction.setAmount(paymentRequestDto.amount());
            transaction.setCurrencyType(paymentRequestDto.currencyType());
            transaction.setTimestamp(LocalDateTime.now());

            Transaction savedTransaction = transactionRepository.save(transaction);
            logger.info("Transaction successfully initiated and saved");

            return transactionMapper.toDto(savedTransaction);

        } catch (Exception e) {
            logger.error("Error initiating payment: {}", e.getMessage(), e);
            throw new RuntimeException("Payment initiation failed", e);
        }
    }
}

