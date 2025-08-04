package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import fr.hb.mlang.projectrestapi.entity.dto.expenseshare.UpdateExpenseSharePaidRequest;
import fr.hb.mlang.projectrestapi.repository.ExpenseShareRepository;
import fr.hb.mlang.projectrestapi.service.ExpenseShareService;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ExpenseShareServiceImpl implements ExpenseShareService {

  private final ExpenseShareRepository repository;

  public ExpenseShareServiceImpl(ExpenseShareRepository repository) {
    this.repository = repository;
  }

  @Override
  public void updateIsPaid(UUID shareId, UpdateExpenseSharePaidRequest request) {
    ExpenseShare share = repository
        .findById(shareId)
        .orElseThrow(() -> new EntityNotFoundException("ExpenseShare not found"));

    share.setIsPaid(request.isPaid());
    repository.save(share);
  }
}
