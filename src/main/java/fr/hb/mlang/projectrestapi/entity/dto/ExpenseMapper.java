package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.Expense;
import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.ExpenseResponseDto;
import fr.hb.mlang.projectrestapi.entity.dto.expenseshare.ExpenseShareReferenceDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, uses = {UserMapper.class})
public interface ExpenseMapper {

  //--- Responses

  @Mapping(source = "paidBy", target = "paidBy")
  @Mapping(source = "expenseShares", target = "expenseShares")
  CreateExpenseResponse expenseToResponseDto(Expense expense);

  @Mapping(source = "debtor", target = "user")
  @Mapping(source = "amountOwed", target = "amountOwed")
  ExpenseShareReferenceDto expenseShareToReferenceDto(ExpenseShare expenseShare);

  ExpenseResponseDto entityToResponseDto(Expense expense);

  List<ExpenseResponseDto> entitiesToResponseDtos(List<Expense> expenses);
}
