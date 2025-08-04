package fr.hb.mlang.projectrestapi.entity.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
    @NotBlank @Email String username,
    @NotBlank String password
) {

}
