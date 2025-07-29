package fr.hb.mlang.projectrestapi.repository;

import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, UUID> {

}
