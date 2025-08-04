package fr.hb.mlang.projectrestapi.entity.dto.user;

import java.util.List;

public record UserDebtsResponse(
    UserReferenceDto user,
    List<UserAmountDto> owes
) {
}
