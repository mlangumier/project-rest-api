package fr.hb.mlang.projectrestapi.entity.dto.expense;

import fr.hb.mlang.projectrestapi.entity.dto.user.UserDebtsResponse;
import java.math.BigDecimal;
import java.util.List;

public record GroupExpensesDetailsResponse(
    BigDecimal total,
    List<ExpenseResponseDto> expenses,
    List<UserDebtsResponse> debts
) {

}
