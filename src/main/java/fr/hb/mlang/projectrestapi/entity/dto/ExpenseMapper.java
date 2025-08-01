package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.Expense;
import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.expense.ExpenseResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expenseshare.ExpenseShareReferenceDto;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ExpenseMapper {

  //--- Responses

  @Mapping(source = "paidBy", target = "paidBy")
  @Mapping(source = "expenseShares", target = "expenseShares")
  ExpenseResponse expenseToResponseDto(Expense expense);

  @Mapping(source = "debtor", target = "user")
  @Mapping(source = "amountOwed", target = "amountOwed")
  ExpenseShareReferenceDto expenseShareToReferenceDto(ExpenseShare expenseShare);

  UserReferenceDto userToUserDto(User user);
}
