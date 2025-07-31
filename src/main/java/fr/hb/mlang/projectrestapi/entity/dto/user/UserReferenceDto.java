package fr.hb.mlang.projectrestapi.entity.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserReferenceDto(
    UUID id,

    @NotBlank(message = "User name is required")
    @Size(min = 2, max = 64, message = "User name must be between 2 and 64 characters long")
    String name,

    @Email(message = "Must be a valid email address")
    String email
) {
}
