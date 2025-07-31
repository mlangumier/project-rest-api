package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.GroupMapper;
import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponseLight;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import fr.hb.mlang.projectrestapi.repository.UserRepository;
import fr.hb.mlang.projectrestapi.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
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
  public List<GroupResponseLight> findAll() {
    return mapper.groupsToResponseLights(groupRepository.findAll());
  }

  @Override
  public GroupResponse findById(UUID id) {
    Group group = groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return mapper.groupToResponseDto(group);
  }

  @Override
  @Transactional
  public GroupResponse create(CreateGroupRequest request) {
    Group group = mapper.createRequestToEntity(request);

    User owner = findUserOrCreateTemporaryUser(request.owner());
    group.setOwner(owner);

    Set<User> members = new HashSet<>();
    members.add(owner);

    if (request.members() != null && !request.members().isEmpty()) {
      for (UserReferenceDto dto : request.members()) {
        members.add(findUserOrCreateTemporaryUser(dto));
      }
    }
    group.setMembers(members);

    Group groupEntity = groupRepository.save(group);

    return mapper.groupToResponseDto(groupEntity);
  }

  /**
   * Gets an owner or a group member from the database if they exist by checking their ID or email.
   * If only the name is given as parameter, creates a temporary user.
   *
   * @param userDto data from the owner or member of the group
   * @return an existing user found by id or email, or a new temporary user
   */
  private User findUserOrCreateTemporaryUser(UserReferenceDto userDto) {
    if (userDto.id() != null) {
      return userRepository.findById(userDto.id()).orElseThrow(EntityNotFoundException::new);
    }

    if (userDto.email() != null) {
      return userRepository.findByEmail(userDto.email()).orElseThrow(EntityNotFoundException::new);
    }

    User temporaryUser = User.temporaryUser(userDto.name());
    return userRepository.save(temporaryUser);
  }
}
