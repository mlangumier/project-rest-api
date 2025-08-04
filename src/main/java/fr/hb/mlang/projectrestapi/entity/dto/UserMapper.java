package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

  //--- Response

  UserReferenceDto entityToUserDto(User user);

  List<UserReferenceDto> usersToUserDtos(Set<User> users);

  @Named("usersToNames")
  static List<String> usersToNames(Set<User> users) {
    return users == null ? List.of() : users.stream().map(User::getName).toList();
  }
}
