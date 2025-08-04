package fr.hb.mlang.projectrestapi.controller;

import fr.hb.mlang.projectrestapi.entity.dto.expenseshare.UpdateExpenseSharePaidRequest;
import fr.hb.mlang.projectrestapi.service.ExpenseShareService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ExpenseShareController {

  private final ExpenseShareService service;

  public ExpenseShareController(ExpenseShareService service) {
    this.service = service;
  }

  @PatchMapping("/expense-shares/{shareId}/paid")
  public ResponseEntity<Void> updateIsPaid(@PathVariable UUID shareId, @Valid @RequestBody UpdateExpenseSharePaidRequest request) {
    service.updateIsPaid(shareId, request);
    return ResponseEntity.noContent().build();
  }
}
