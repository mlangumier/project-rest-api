package fr.hb.mlang.projectrestapi.entity.dto.group;

import fr.hb.mlang.projectrestapi.entity.dto.user.SimpleUserDto;
import java.util.Set;
import java.util.UUID;

public record GroupResponse(
    UUID id,
    String name,
    SimpleUserDto ownerId,
    Set<SimpleUserDto> members
) {

}
