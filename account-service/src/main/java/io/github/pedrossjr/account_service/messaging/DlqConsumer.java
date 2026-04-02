package io.github.pedrossjr.account_service.messaging;

import io.github.pedrossjr.common.event.PaymentCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DlqConsumer {

    @KafkaListener(topics = "payment-created.DLT", groupId = "dlq-group")
    public void consume(PaymentCreatedEvent event) {

        System.out.println("payment-created.DLT: " + event);

    }
}
