package fr.hb.mlang.projectrestapi.entity.dto.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UpdateGroupRequest(
    @NotNull(message = "Group must have a valid id")
    UUID id,

    @NotBlank(message = "Group requires a name")
    @Size(min = 4, max = 64, message = "Group name must be between 4 and 64 characters long.")
    String name,

    @NotNull(message = "Group must have an owner")
    UUID ownerId

    //@NotEmpty(message = "Group must have at least 1 participant")
    //Set<UUID> members

    //Set<UUID> expenses
    //Set<UUID> settlements
) {

}
