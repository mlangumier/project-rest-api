package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.GroupMapper;
import fr.hb.mlang.projectrestapi.entity.dto.group.GroupResponseLight;
import fr.hb.mlang.projectrestapi.repository.UserRepository;
import fr.hb.mlang.projectrestapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository repository;
  private final GroupMapper groupMapper;

  public UserServiceImpl(UserRepository repository, GroupMapper groupMapper) {
    this.repository = repository;
    this.groupMapper = groupMapper;
  }

  @Override
  public List<GroupResponseLight> getUserGroups(UUID userId) {
    User user = repository
        .findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return user.getGroups().stream().map(groupMapper::groupToResponseLight).toList();
  }
}
