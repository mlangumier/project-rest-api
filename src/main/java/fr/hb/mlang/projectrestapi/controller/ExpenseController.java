package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.ExpenseResponse;
import fr.hb.mlang.projectrestapi.service.ExpenseService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

  private final ExpenseService expenseService;

  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @PostMapping("/groups/{groupId}/expenses")
  public ResponseEntity<ExpenseResponse> createExpense(
      @PathVariable UUID groupId,
      @Valid @RequestBody CreateExpenseRequest request
  ) {
    ExpenseResponse response = expenseService.create(groupId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
