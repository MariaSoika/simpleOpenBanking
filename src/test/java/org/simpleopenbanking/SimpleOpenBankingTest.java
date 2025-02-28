package org.simpleopenbanking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simpleopenbanking.dto.AccountDto;
import org.simpleopenbanking.dto.PaymentRequestDto;
import org.simpleopenbanking.dto.TransactionDto;
import org.simpleopenbanking.entity.Account;
import org.simpleopenbanking.entity.Transaction;
import org.simpleopenbanking.enums.CurrencyType;
import org.simpleopenbanking.mapper.AccountMapper;
import org.simpleopenbanking.mapper.TransactionMapper;
import org.simpleopenbanking.repository.AccountRepository;
import org.simpleopenbanking.repository.TransactionRepository;
import org.simpleopenbanking.service.AccountService;
import org.simpleopenbanking.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SimpleOpenBankingTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private final WebClient webClient = WebClient.create();
    //"UA729170132775421511313212280"
    //"UA165808213459573340183621023"
    @Test
    public void testInitiatePayment_Success() {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto("UA729170132775421511313212280" , "UA165808213459573340183621023", 1000L, CurrencyType.UAH);

        Mockito.when(webClient.get()
                .uri("http://localhost:8080/api/accounts/{accountIban}/balance", paymentRequestDto.ibanFrom())
                .retrieve()
                .bodyToMono(AccountDto.class)
                .flatMap(response -> Mono.just(response.balance()))
                .block()
        ).thenReturn(500L);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            transactionService.initiatePayment(paymentRequestDto);
        });
    }

    @Test
    public void testInitiatePayment_FailedSameIban() {
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto("UA729170132775421511313212280" , "UA729170132775421511313212280" , 100L, CurrencyType.UAH);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionService.initiatePayment(paymentRequestDto);
        });
    }

    @Test
    public void testGetAccountByIBAN_Success() {
        AccountDto accountDto = new AccountDto("UA729170132775421511313212280" , 100L, CurrencyType.UAH);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account()));
        Mockito.when(accountMapper.toDto(Mockito.any())).thenReturn(accountDto);

        AccountDto result = accountService.getAccountByIBAN("UA729170132775421511313212280" );

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.iban());
        Assertions.assertEquals(100L, result.balance());
    }

    @Test
    public void testGetLast10Transactions_Success() {
        List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction(), new Transaction());
        Page<Transaction> page = new PageImpl<>(transactions);
        Mockito.when(transactionRepository.findTop10ByReceiverAccountIbanOrderByTimestampDesc(Mockito.any(), Mockito.any()))
                .thenReturn(page);

        Page<TransactionDto> result = transactionService.getLast10Transactions("UA729170132775421511313212280" );

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getTotalElements());
    }
}
