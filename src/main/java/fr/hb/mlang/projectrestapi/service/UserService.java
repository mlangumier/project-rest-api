package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponseLight;
import java.util.List;
import java.util.UUID;

public interface UserService {

  List<GroupResponseLight> getUserGroups(UUID userId);
}
