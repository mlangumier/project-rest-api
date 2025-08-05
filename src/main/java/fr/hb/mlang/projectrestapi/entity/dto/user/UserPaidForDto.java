package fr.hb.mlang.projectrestapi.entity.dto.user;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UserPaidForDto(
    @NotBlank(message = "User must have a name")
    String name,

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    BigDecimal amountOwed
) {

}
