package fr.hb.mlang.projectrestapi.entity.dto.user;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record UserPaidForDto(
    @NotNull
    UUID id,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal amount
) {

}
