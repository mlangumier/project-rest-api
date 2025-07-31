package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponseLight;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GroupMapper {

  //--- Requests

  Group createRequestToEntity(CreateGroupRequest request);

  User userDtoToUser(UserReferenceDto dto);

  Set<User> userDtosToUsers(List<UserReferenceDto> dtos);

  //--- Responses

  @Mapping(source = "owner", target = "owner")
  @Mapping(source = "members", target = "members")
  GroupResponse groupToResponseDto(Group group);

  UserReferenceDto userToUserDto(User user);

  List<UserReferenceDto> usersToUserDtos(Set<User> users);

  @Mapping(source = "owner.name", target = "owner")
  @Mapping(source = "members", target = "members", qualifiedByName = "usersToNames")
  GroupResponseLight groupToResponseLight(Group groups);

  List<GroupResponseLight> groupsToResponseLights(List<Group> groups);

  @Named("usersToNames")
  static List<String> usersToNames(Set<User> users) {
    return users == null ? List.of() : users.stream().map(User::getName).toList();
  }
}
