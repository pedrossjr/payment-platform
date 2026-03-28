package io.github.pedrossjr.account_service.service;

import io.github.pedrossjr.account_service.entity.Account;
import io.github.pedrossjr.account_service.repository.AccountRepository;
import io.github.pedrossjr.common.event.PaymentCreatedEvent;
import io.github.pedrossjr.common.event.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(
        topicPartitions = @TopicPartition(
            topic = "payment-created",
            partitions = {"0"}),
        containerFactory = "paymentKafkaListenerContainerFactory"
    )
    public void processPayment(PaymentCreatedEvent paymentCreatedEvent) {
        try {
            Account account = repository.findById(paymentCreatedEvent.getAccountId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

            if (account.getBalance().compareTo(paymentCreatedEvent.getAmount()) < 0) {
                sendProcessedEvent(paymentCreatedEvent, "FAILED");
                return;
            }

            account.setBalance(account.getBalance().subtract(paymentCreatedEvent.getAmount()));
            repository.save(account);

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
}