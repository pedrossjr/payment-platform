package io.github.pedrossjr.account_service.service;

import io.github.pedrossjr.account_service.entity.Account;
import io.github.pedrossjr.account_service.record.AccountRecord;
import io.github.pedrossjr.account_service.repository.AccountRepository;
import io.github.pedrossjr.common.event.PaymentCreatedEvent;
import io.github.pedrossjr.common.event.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(
        topicPartitions = @TopicPartition(
            topic = "payment-created",
            partitions = {"0"}),
        containerFactory = "paymentKafkaListenerContainerFactory"
    )
    public void processPayment(PaymentCreatedEvent paymentCreatedEvent) {
        try {
            Account account = accountRepository.findById(paymentCreatedEvent.getAccountId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

            if (account.getBalance().compareTo(paymentCreatedEvent.getAmount()) < 0) {
                sendProcessedEvent(paymentCreatedEvent, "FAILED");
                return;
            }

            account.setBalance(account.getBalance().subtract(paymentCreatedEvent.getAmount()));
            accountRepository.save(account);

            sendProcessedEvent(paymentCreatedEvent, "COMPLETED");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendProcessedEvent(PaymentCreatedEvent event, String status) {

        PaymentProcessedEvent processedEvent =
            new PaymentProcessedEvent(event.getPaymentId(), status);

        kafkaTemplate.send("payment-processed", processedEvent);
    }

    public Account createAccount(AccountRecord accountRecord) {

        String accountNumber = this.createAccountNumber();

        boolean accountExists = accountRepository.existsByAccountNumber(accountNumber);

        if(accountExists) {
            throw new RuntimeException("Número de conta já existe. Tente novamente.");
        }

        Account account = new Account();
        account.setUserId(accountRecord.userId());
        account.setAccountNumber(accountNumber);
        account.setBalance(accountRecord.balance());
        Account accountCreated =  accountRepository.save(account);
        kafkaTemplate.send("account-created", accountCreated);
        return accountCreated;
    }

    public String createAccountNumber() {
        Integer minimo = 100000;
        Integer maximo = 999999;

        Random random = new Random();

        String accountNumber = String.valueOf(random.nextInt(maximo - minimo + 1) + minimo);
        String accountDigit = String.valueOf(random.nextInt(9 - 0 + 1) + 0);
        return accountNumber + "-" + accountDigit;
    }

    public Account getAccountNumber(String accountNumber) {
        try{
            return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Account> getAllAccounts() {
        try{
            List<Account> accounts = accountRepository.findAll();

            if(accounts.isEmpty())       {
                throw new RuntimeException("Nenhuma conta encontrada.");
            }

            return accounts;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}