package fr.hb.mlang.projectrestapi.entity.dto.expense;

import fr.hb.mlang.projectrestapi.entity.dto.user.UserPaidForDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateExpenseRequest(
    @NotBlank(message = "Expense must have a label or short description")
    String name,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal amount,

    @NotNull
    UUID paidById,

    @NotNull
    UUID groupId,

    @Valid
    @NotEmpty(message = "Expense must concern multiple group members")
    List<UserPaidForDto> userPaidFor
    ) {

}
