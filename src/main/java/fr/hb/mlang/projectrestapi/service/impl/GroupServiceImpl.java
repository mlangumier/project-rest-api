package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.GroupMapper;
import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import fr.hb.mlang.projectrestapi.entity.dto.group.UpdateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import fr.hb.mlang.projectrestapi.repository.UserRepository;
import fr.hb.mlang.projectrestapi.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  private final GroupMapper mapper;
  private final GroupRepository groupRepository;
  private final UserRepository userRepository;

  public GroupServiceImpl(
      GroupMapper mapper,
      GroupRepository groupRepository,
      UserRepository userRepository
  ) {
    this.mapper = mapper;
    this.groupRepository = groupRepository;
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public GroupResponse createGroup(CreateGroupRequest request) {
    Group group = mapper.toEntity(request);

    User owner = resolveUser(request.owner());
    group.setOwner(owner);

    Set<User> members = new HashSet<>();
    members.add(owner);

    if (request.members() != null && !request.members().isEmpty()) {
      for (UserReferenceDto dto : request.members()) {
        members.add(resolveUser(dto));
      }
    }

    group.setMembers(members);

    Group groupEntity = groupRepository.save(group);
    return null;
  }

  /**
   * Gets an owner or a group member from the database if they exist by checking their ID or email.
   * If only the name is given as parameter, creates a temporary user.
   *
   * @param userDto data from the owner or member of the group
   * @return an existing user found by id or email, or a new temporary user
   */
  private User resolveUser(UserReferenceDto userDto) {
    if (userDto.id() != null) {
      return userRepository
          .findById(userDto.id())
          .orElseThrow(() -> new EntityNotFoundException(
              "Couldn't find user of id: " + userDto.id()));
    }

    if (userDto.email() != null) {
      return userRepository
          .findByEmail(userDto.email())
          .orElseThrow(() -> new EntityNotFoundException(
              "Couldn't find user with email: " + userDto.email()));
    }

    User temporaryUser = User.temporaryUser(userDto.name());
    return userRepository.save(temporaryUser);
  }

  @Override
  public GroupResponse updateGroup(UUID groupId, UpdateGroupRequest request) {
    //Group group = groupRepository.findById(groupId).orElseThrow(); // Do a reusable method
    // Mapper: RequestDTO -> Entity
    // return ResponseDto(Entity)

    return null;
  }
}
