package io.github.pedrossjr.payment_service.service;

import io.github.pedrossjr.common.record.PaymentRecord;
import io.github.pedrossjr.payment_service.entity.Payment;
import io.github.pedrossjr.payment_service.messaging.PaymentProducer;
import io.github.pedrossjr.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final IdempotencyService idempotencyService;
    private final PaymentProducer paymentProducer;

    public String createPayment(PaymentRecord request, String idempotencyKey) {

        var existing = idempotencyService.check(idempotencyKey);

        if(existing.isPresent()) {
            return existing.get();
        }

        Payment payment = new Payment();
        payment.setExternalId(UUID.randomUUID().toString());
        payment.setAccountId(request.accountId());
        payment.setAmount(request.amount());
        payment.setStatus("PENDING");
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        String response = "create-payment: " + payment.getExternalId();

        idempotencyService.save(idempotencyKey, response);

        paymentProducer.sendPaymentCreatedEvent(payment);

        return response;
    }
}