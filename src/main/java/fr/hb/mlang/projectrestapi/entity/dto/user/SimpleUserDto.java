package fr.hb.mlang.projectrestapi.entity.dto.user;

import java.util.UUID;

public record SimpleUserDto(
    UUID id,
    String name
) {
}
