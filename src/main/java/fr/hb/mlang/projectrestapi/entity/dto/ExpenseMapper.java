package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.Expense;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.ExpenseResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, uses = {UserMapper.class, ExpenseShareMapper.class})
public interface ExpenseMapper {

  //--- Responses

  @Mapping(source = "paidBy", target = "paidBy")
  @Mapping(source = "expenseShares", target = "expenseShares")
  CreateExpenseResponse expenseToResponseDto(Expense expense);

  ExpenseResponseDto entityToResponseDto(Expense expense);

  List<ExpenseResponseDto> entitiesToResponseDtos(List<Expense> expenses);
}
