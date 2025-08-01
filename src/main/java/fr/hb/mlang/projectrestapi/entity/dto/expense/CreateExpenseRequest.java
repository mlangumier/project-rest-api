package fr.hb.mlang.projectrestapi.entity.dto.expense;

import fr.hb.mlang.projectrestapi.entity.dto.user.UserPaidForDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateExpenseRequest(
    @NotBlank(message = "Expense must have a label or short description")
    @Size(min = 2, max = 64, message = "Expense name must be between 2 and 64 characters long")
    String name,

    @NotNull(message = "Expense must contain an amount")
    @DecimalMin(value = "0.01", message = "Expense must be of a positive amount")
    BigDecimal amount,

    @NotNull(message = "Expense must be done by someone")
    UUID paidById,

    @Valid
    @NotEmpty(message = "Expense must concern multiple group members")
    List<UserPaidForDto> usersPaidFor
) {

}
