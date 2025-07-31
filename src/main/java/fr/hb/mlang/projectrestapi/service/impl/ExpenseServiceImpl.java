package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.ExpenseResponse;
import fr.hb.mlang.projectrestapi.repository.ExpenseRepository;
import fr.hb.mlang.projectrestapi.service.ExpenseService;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;

  public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
  }

  @Override
  public ExpenseResponse create(CreateExpenseRequest request) {
    //

    return null;
  }
}
