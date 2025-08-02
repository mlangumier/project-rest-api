package fr.hb.mlang.projectrestapi.entity.dto.expense;

import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ExpenseResponseDto(
    UUID id,
    String name,
    BigDecimal amount,
    LocalDateTime paidAt,
    UserReferenceDto paidBy
) {

}
