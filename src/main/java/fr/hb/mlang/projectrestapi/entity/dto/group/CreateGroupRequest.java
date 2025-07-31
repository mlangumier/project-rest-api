package fr.hb.mlang.projectrestapi.entity.dto.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateGroupRequest(
    @NotBlank(message = "Group requires a name ")
    @Size(min = 4, max = 64, message = "Group name must be between 4 and 64 characters long.")
    String name,

    @NotNull(message = "Group must have an owner")
    UUID ownerId

    //List<UUID> members
) {

}
