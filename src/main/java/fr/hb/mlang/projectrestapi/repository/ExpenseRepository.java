package fr.hb.mlang.projectrestapi.repository;

import fr.hb.mlang.projectrestapi.entity.Expense;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

  @Query("""
      SELECT e
      FROM Expense e
      WHERE e.group.id = :groupId
            AND (:paidByName IS NULL OR e.paidBy.name = :paidByName)
            AND (:minAmount IS NULL OR e.amount >= :minAmount)
            AND (:maxAmount IS NULL OR e.amount <= :maxAmount)
      """)
  List<Expense> findGroupExpenses(
      UUID groupId,
      String paidByName,
      BigDecimal minAmount,
      BigDecimal maxAmount
  );
}
