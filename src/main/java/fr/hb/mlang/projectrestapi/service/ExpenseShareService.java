package fr.hb.mlang.projectrestapi.service;

import fr.hb.mlang.projectrestapi.entity.dto.expenseshare.UpdateExpenseSharePaidRequest;
import java.util.UUID;

public interface ExpenseShareService {

  void updateIsPaid(UUID shareId, UpdateExpenseSharePaidRequest request);

}
