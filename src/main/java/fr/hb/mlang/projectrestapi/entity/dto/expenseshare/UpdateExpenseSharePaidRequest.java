package fr.hb.mlang.projectrestapi.entity.dto.expenseshare;

import jakarta.validation.constraints.NotNull;

public record UpdateExpenseSharePaidRequest(
    @NotNull Boolean isPaid
) {

}
