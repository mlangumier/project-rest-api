package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.GroupExpensesResponse;
import java.math.BigDecimal;
import java.util.UUID;

public interface ExpenseService {

  GroupExpensesResponse getFromGroup(UUID groupId, UUID userId, BigDecimal minAmount, BigDecimal maxAmount);

  CreateExpenseResponse create(UUID groupId, CreateExpenseRequest request);
}
