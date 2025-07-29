package fr.hb.mlang.projectrestapi.repository;

import fr.hb.mlang.projectrestapi.entity.Group;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, UUID> {

}
