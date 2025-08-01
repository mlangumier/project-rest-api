package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.ExpenseResponse;
import java.util.UUID;

public interface ExpenseService {

  ExpenseResponse create(UUID groupId, CreateExpenseRequest request);
}
