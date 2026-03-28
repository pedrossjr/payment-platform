package io.github.pedrossjr.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreatedEvent {

    private Long paymentId;
    private Long accountId;
    private BigDecimal amount;

}