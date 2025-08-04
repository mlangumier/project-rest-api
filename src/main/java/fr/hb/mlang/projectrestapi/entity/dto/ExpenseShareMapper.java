package fr.hb.mlang.projectrestapi.entity.dto;

import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import fr.hb.mlang.projectrestapi.entity.dto.expenseshare.ExpenseShareReferenceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING, uses = {UserMapper.class})
public interface ExpenseShareMapper {

  @Mapping(source = "debtor", target = "user")
  @Mapping(source = "amountOwed", target = "amountOwed")
  ExpenseShareReferenceDto expenseShareToReferenceDto(ExpenseShare expenseShare);
}
