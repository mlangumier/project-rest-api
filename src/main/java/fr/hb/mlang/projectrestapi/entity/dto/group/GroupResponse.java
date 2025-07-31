package fr.hb.mlang.projectrestapi.entity.dto.group;

import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import java.util.List;
import java.util.UUID;

public record GroupResponse(
    UUID id,
    String name,
    UserReferenceDto owner,
    List<UserReferenceDto> members
) {

}
