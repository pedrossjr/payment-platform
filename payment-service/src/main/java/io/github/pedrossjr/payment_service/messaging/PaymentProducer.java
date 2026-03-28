package io.github.pedrossjr.payment_service.messaging;

import io.github.pedrossjr.common.event.PaymentCreatedEvent;
import io.github.pedrossjr.payment_service.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendPaymentCreatedEvent(Payment payment) {

        PaymentCreatedEvent event = new PaymentCreatedEvent(
            payment.getId(),
            payment.getAccountId(),
            payment.getAmount()
        );

        kafkaTemplate.send("payment-created", event);
    }
}
