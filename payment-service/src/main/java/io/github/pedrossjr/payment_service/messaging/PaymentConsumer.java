package io.github.pedrossjr.payment_service.messaging;

import io.github.pedrossjr.common.event.PaymentProcessedEvent;
import io.github.pedrossjr.payment_service.entity.Payment;
import io.github.pedrossjr.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentRepository repository;

    @KafkaListener(
        topicPartitions = @TopicPartition(
            topic = "payment-processed",
            partitions = { "1" }
        ),
        containerFactory = "paymentKafkaListenerContainerFactory",
        groupId = "payment-group"
    )
    public void consume(PaymentProcessedEvent paymentProcessedEvent) {

        Payment payment = repository.findById(paymentProcessedEvent.getPaymentId())
            .orElseThrow();

        payment.setStatus(paymentProcessedEvent.getStatus());
        repository.save(payment);
    }
}
