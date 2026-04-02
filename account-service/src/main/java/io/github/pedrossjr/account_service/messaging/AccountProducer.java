package io.github.pedrossjr.account_service.messaging;

import io.github.pedrossjr.account_service.entity.Account;
import io.github.pedrossjr.common.event.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

        public void sendAccountCreatedEvent(Account account) {

            AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(
                account.getUserId(),
                account.getAccountNumber(),
                account.getBalance()
            );

            kafkaTemplate.send("account-created", accountCreatedEvent);
        }
}
