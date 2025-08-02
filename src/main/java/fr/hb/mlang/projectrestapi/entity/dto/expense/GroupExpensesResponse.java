package fr.hb.mlang.projectrestapi.entity.dto.expense;

import java.math.BigDecimal;
import java.util.List;

public record GroupExpensesResponse(
    List<ExpenseResponseDto> expenses,
    BigDecimal total
) {
}
