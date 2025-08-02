package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.entity.Expense;
import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.ExpenseMapper;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.GroupExpensesResponse;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserPaidForDto;
import fr.hb.mlang.projectrestapi.repository.ExpenseRepository;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import fr.hb.mlang.projectrestapi.service.ExpenseService;
import fr.hb.mlang.projectrestapi.utils.MoneyUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final GroupRepository groupRepository;
  private final ExpenseMapper mapper;

  public ExpenseServiceImpl(ExpenseRepository expenseRepository, GroupRepository groupRepository, ExpenseMapper mapper) {
    this.expenseRepository = expenseRepository;
    this.groupRepository = groupRepository;
    this.mapper = mapper;
  }

  @Override
  public GroupExpensesResponse getFromGroup(UUID groupId, UUID userId, BigDecimal minAmount, BigDecimal maxAmount) {
    Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);

    List<Expense> expenses = expenseRepository.findGroupExpenses(group.getId(), userId, minAmount, maxAmount);
    BigDecimal total = expenses.stream().map(Expense::getAmount).reduce(MoneyUtils.of(0), BigDecimal::add);

    return new GroupExpensesResponse(mapper.entitiesToResponseDtos(expenses), total);
  }

  @Override
  @Transactional
  public CreateExpenseResponse create(UUID groupId, CreateExpenseRequest request) {
    // Fetch group
    Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);

    // Check that all paidForUser exist in the group
    Set<UUID> groupMemberIds = group
        .getMembers()
        .stream()
        .map(User::getId)
        .collect(Collectors.toSet())
        ;

    for (UserPaidForDto dto : request.usersPaidFor()) {
      if (!groupMemberIds.contains(dto.id())) {
        throw new RuntimeException("User not find in group");
      }
    }

    // Check that paidByUserId exists in the group
    User paidByUser = group
        .getMembers()
        .stream()
        .filter(user -> user.getId().equals(request.paidById()))
        .findFirst()
        .orElseThrow(EntityNotFoundException::new)
        ;

    // Create expense
    Expense expense = new Expense();
    expense.setName(request.name());
    expense.setAmount(request.amount());
    expense.setGroup(group);
    expense.setPaidBy(paidByUser);

    // Create expenseShares & add them to the expense
    for (UserPaidForDto userDto : request.usersPaidFor()) {
      User member = group
          .getMembers()
          .stream()
          .filter(user -> user.getId().equals(userDto.id()))
          .findFirst()
          .orElseThrow(EntityNotFoundException::new)
          ;

      ExpenseShare share = new ExpenseShare();
      share.setDebtor(member);
      share.setAmountOwed(userDto.amountOwed());
      share.setIsPaid(member.getId().equals(paidByUser.getId()));

      expense.addExpenseShare(share);
    }

    // Add the expenseShares to the expense & save
    Expense savedExpense = expenseRepository.save(expense);
    System.err.println("> Shares: " + savedExpense.getExpenseShares());
    return mapper.expenseToResponseDto(savedExpense);
  }
}
