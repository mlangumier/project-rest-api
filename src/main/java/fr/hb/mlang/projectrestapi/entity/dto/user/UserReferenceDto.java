package fr.hb.mlang.projectrestapi.entity.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserReferenceDto(
    String id,
    String name,
    String email
) {

}
