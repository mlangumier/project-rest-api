package fr.hb.mlang.projectrestapi.entity.dto.expenseshare;

import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseShareReferenceDto(
    UUID id,
    BigDecimal amountOwed,
    boolean isPaid,
    UserReferenceDto user
) {

}
