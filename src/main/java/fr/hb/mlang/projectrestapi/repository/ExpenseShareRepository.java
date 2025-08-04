package fr.hb.mlang.projectrestapi.repository;

import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, UUID> {

  @Query("""
      SELECT s
      FROM ExpenseShare s
      WHERE s.debtor.id = :userId
            AND s.expense.group.id = :groupId
            AND s.isPaid = false 
      """)
  List<ExpenseShare> findAllUnpaidByUserIdAndGroupId(UUID userId, UUID groupId);
}
