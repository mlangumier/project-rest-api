package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.group.CreateGroupRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponseLight;
import java.util.List;
import java.util.UUID;

public interface GroupService {

  List<GroupResponseLight> findAll();

  GroupResponse findById(UUID id);

  GroupResponse create(CreateGroupRequest request);

  // update

  // delete
}
