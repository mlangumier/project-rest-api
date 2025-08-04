package fr.hb.mlang.projectrestapi.entity.dto.user;

import java.math.BigDecimal;

public record UserAmountDto(
    UserReferenceDto user,
    BigDecimal amount
) {

}
