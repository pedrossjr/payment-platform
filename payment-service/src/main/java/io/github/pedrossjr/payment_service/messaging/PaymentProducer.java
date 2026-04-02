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

        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent(
            payment.getId(),
            payment.getAccountNumber(),
            payment.getAmount()
        );

        kafkaTemplate.send("payment-created", paymentCreatedEvent);
    }
}
