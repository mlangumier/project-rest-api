package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.user.SimpleUserDto;
import fr.hb.mlang.projectrestapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UserMapperHelper {

  private final UserRepository userRepository;

  public UserMapperHelper(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Maps the User's UUID into the {@link User} entity.
   *
   * @param ownerId UUID of the user
   * @return the found <code>User</code> entity
   */
  public User map(UUID ownerId) {
    return userRepository
        .findById(ownerId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + ownerId));
  }

  public SimpleUserDto map(User owner) {
    return new SimpleUserDto(owner.getId(), owner.getName());
  }

  //TODO: from Set<UUID> to Set<User>
  //TODO: from Set<User> to Set<SimpleUserDto>

}
