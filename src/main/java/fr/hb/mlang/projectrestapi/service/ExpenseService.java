package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.GroupExpensesDetailsResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.GroupExpensesResponse;
import java.math.BigDecimal;
import java.util.UUID;

public interface ExpenseService {

  GroupExpensesResponse getFromGroup(UUID groupId, String paidByName, BigDecimal minAmount, BigDecimal maxAmount);

  GroupExpensesDetailsResponse getDetailsFromGroup(UUID groupId);

  CreateExpenseResponse create(UUID groupId, CreateExpenseRequest request);
}
