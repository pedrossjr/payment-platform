package io.github.pedrossjr.account_service.record;

import java.math.BigDecimal;

public record AccountTransferRecord(
    String fromAccountNumber,
    String toAccountNumber,
    BigDecimal amount
) {
}
