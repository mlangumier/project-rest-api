package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupCreateRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, uses = UserMapperHelper.class)
public interface GroupMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "ownerId", target = "owner")
  //@Mapping(source = "participants", target = "participants")
  @Mapping(target = "expenses", ignore = true)
  @Mapping(target = "settlements", ignore = true)
  Group toEntity(GroupCreateRequest dto);

  @Mapping(source = "owner.id", target = "ownerId")
  @Mapping(source = "participants", target = "participants")
  GroupResponse toResponse(Group group);
}
