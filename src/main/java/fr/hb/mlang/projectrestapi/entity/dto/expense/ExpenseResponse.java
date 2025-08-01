package fr.hb.mlang.projectrestapi.entity.dto.expense;

import fr.hb.mlang.projectrestapi.entity.dto.expenseshare.ExpenseShareReferenceDto;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ExpenseResponse(
    UUID id,
    String name,
    BigDecimal amount,
    LocalDateTime paidAt,
    UserReferenceDto paidBy,
    List<ExpenseShareReferenceDto> expenseShares
) {

}
