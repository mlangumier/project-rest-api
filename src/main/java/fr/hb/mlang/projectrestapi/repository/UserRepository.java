package fr.hb.mlang.projectrestapi.repository;

import fr.hb.mlang.projectrestapi.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<UserDetails> findByEmail(String email);
}
