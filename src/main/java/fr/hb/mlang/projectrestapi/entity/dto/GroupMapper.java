package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GroupMapper {

  Group toEntity(CreateGroupRequest request);

  User toEntity(UserReferenceDto dto);

  Set<User> toEntities(List<UserReferenceDto> dtos);

  //@Mapping(source = "owner.id", target = "ownerId")
  //@Mapping(source = "members", target = "members")
  //GroupResponse entityToResponseDTO(Group group);
}
