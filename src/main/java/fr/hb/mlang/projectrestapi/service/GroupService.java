package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.group.GroupCreateRequest;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponse;

public interface GroupService {

  GroupResponse createGroup(GroupCreateRequest request);

  GroupResponse updateGroup();
}
