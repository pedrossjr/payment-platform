package io.github.pedrossjr.account_service.record;

import java.math.BigDecimal;

public record AccountRecord(
    Long userId,
    BigDecimal balance
) {
}
