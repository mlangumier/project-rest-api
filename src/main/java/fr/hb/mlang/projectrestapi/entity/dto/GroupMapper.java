package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, uses = UserMapperHelper.class)
public interface GroupMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "ownerId", target = "owner")
  //@Mapping(source = "members", target = "members")
  @Mapping(target = "expenses", ignore = true)
  @Mapping(target = "settlements", ignore = true)
  Group createDTOtoEntity(CreateGroupRequest dto);

  @Mapping(source = "owner.id", target = "ownerId")
  @Mapping(source = "members", target = "members")
  GroupResponse entityToResponseDTO(Group group);
}
