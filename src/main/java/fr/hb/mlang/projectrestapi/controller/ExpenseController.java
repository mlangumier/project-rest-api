package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.GroupExpensesResponse;
import fr.hb.mlang.projectrestapi.service.ExpenseService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

  private final ExpenseService expenseService;

  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @GetMapping("/groups/{groupId}/expenses")
  public ResponseEntity<GroupExpensesResponse> getGroupExpenses(
      @PathVariable UUID groupId,
      @RequestParam(required = false) UUID paidBy,
      @RequestParam(required = false)BigDecimal minAmount,
      @RequestParam(required = false)BigDecimal maxAmount
      ) {
    GroupExpensesResponse response = expenseService.getFromGroup(groupId, paidBy, minAmount, maxAmount);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/groups/{groupId}/expenses")
  public ResponseEntity<CreateExpenseResponse> createExpense(
      @PathVariable UUID groupId,
      @Valid @RequestBody CreateExpenseRequest request
  ) {
    CreateExpenseResponse response = expenseService.create(groupId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
