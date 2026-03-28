package io.github.pedrossjr.account_service.messaging;

import io.github.pedrossjr.account_service.service.AccountService;
import io.github.pedrossjr.common.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final AccountService accountService;

    @KafkaListener(
        topics = "payment-created",
        groupId = "account-group",
        containerFactory = "paymentKafkaListenerContainerFactory"
    )
    public void paymentConsume(PaymentCreatedEvent paymentCreatedEvent) {
        accountService.processPayment(paymentCreatedEvent);
    }
}