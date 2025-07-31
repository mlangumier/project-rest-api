package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import fr.hb.mlang.projectrestapi.entity.dto.group.UpdateGroupRequest;
import java.util.UUID;

public interface GroupService {

  GroupResponse createGroup(CreateGroupRequest request);

  GroupResponse updateGroup(UUID groupId, UpdateGroupRequest request);
}
