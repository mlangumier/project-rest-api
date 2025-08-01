package fr.hb.mlang.projectrestapi.entity.dto.user;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record UserPaidForDto(
    @NotNull(message = "User must have an id")
    UUID id,

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    BigDecimal amountOwed
) {

}
