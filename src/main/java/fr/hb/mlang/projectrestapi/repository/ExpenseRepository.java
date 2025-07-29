package fr.hb.mlang.projectrestapi.repository;

import fr.hb.mlang.projectrestapi.entity.Expense;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

}
