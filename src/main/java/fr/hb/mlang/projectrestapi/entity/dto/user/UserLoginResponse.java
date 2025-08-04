package fr.hb.mlang.projectrestapi.entity.dto.user;

public record UserLoginResponse(
    UserReferenceDto user,
    String token
) {

}
