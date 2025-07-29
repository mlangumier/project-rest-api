package fr.hb.mlang.projectrestapi.repository;

import fr.hb.mlang.projectrestapi.entity.Settlement;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, UUID> {

}
