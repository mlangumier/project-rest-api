package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.ExpenseResponse;

public interface ExpenseService {

  ExpenseResponse create(CreateExpenseRequest request);
}
