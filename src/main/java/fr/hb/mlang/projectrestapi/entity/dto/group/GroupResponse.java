package fr.hb.mlang.projectrestapi.entity.dto.group;

import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import java.util.Set;
import java.util.UUID;

public record GroupResponse(
    UUID id,
    String name,
    UserReferenceDto ownerId,
    Set<UserReferenceDto> members
) {

}
