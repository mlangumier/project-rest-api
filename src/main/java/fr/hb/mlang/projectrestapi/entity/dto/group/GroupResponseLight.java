package fr.hb.mlang.projectrestapi.entity.dto.group;

import java.util.List;
import java.util.UUID;

public record GroupResponseLight(
    UUID id,
    String name,
    String owner,
    List<String> members
) {

}
