package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.dto.GroupMapper;
import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import fr.hb.mlang.projectrestapi.entity.dto.group.UpdateGroupRequest;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import fr.hb.mlang.projectrestapi.service.GroupService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;

  public GroupServiceImpl(
      GroupRepository groupRepository,
      GroupMapper groupMapper
  ) {
    this.groupRepository = groupRepository;
    this.groupMapper = groupMapper;
  }

  @Override
  public GroupResponse createGroup(CreateGroupRequest request) {
    Group group = groupMapper.createDTOtoEntity(request);
    // If
    group.addParticipant(group.getOwner()); // Owner is the first user added list of members
    Group createdGroup = groupRepository.save(group);
    return groupMapper.entityToResponseDTO(createdGroup);
  }

  @Override
  public GroupResponse updateGroup(UUID groupId, UpdateGroupRequest request) {
    Group group = groupRepository.findById(groupId).orElseThrow(); // Do a reusable method
    // Mapper: RequestDTO -> Entity
    // return ResponseDto(Entity)

    return null;
  }
}
