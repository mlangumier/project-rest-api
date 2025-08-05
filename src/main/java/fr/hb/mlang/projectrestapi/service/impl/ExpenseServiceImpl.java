package fr.hb.mlang.projectrestapi.service.impl;

import fr.hb.mlang.projectrestapi.entity.Expense;
import fr.hb.mlang.projectrestapi.entity.ExpenseShare;
import fr.hb.mlang.projectrestapi.entity.Group;
import fr.hb.mlang.projectrestapi.entity.User;
import fr.hb.mlang.projectrestapi.entity.dto.ExpenseMapper;
import fr.hb.mlang.projectrestapi.entity.dto.UserMapper;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseRequest;
import fr.hb.mlang.projectrestapi.entity.dto.expense.CreateExpenseResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.GroupExpensesDetailsResponse;
import fr.hb.mlang.projectrestapi.entity.dto.expense.GroupExpensesResponse;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserAmountDto;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserDebtsResponse;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserPaidForDto;
import fr.hb.mlang.projectrestapi.entity.dto.user.UserReferenceDto;
import fr.hb.mlang.projectrestapi.repository.ExpenseRepository;
import fr.hb.mlang.projectrestapi.repository.ExpenseShareRepository;
import fr.hb.mlang.projectrestapi.repository.GroupRepository;
import fr.hb.mlang.projectrestapi.service.ExpenseService;
import fr.hb.mlang.projectrestapi.utils.MoneyUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final GroupRepository groupRepository;
  private final ExpenseShareRepository shareRepository;
  private final ExpenseMapper mapper;
  private final UserMapper userMapper;

  public ExpenseServiceImpl(
      ExpenseRepository expenseRepository,
      GroupRepository groupRepository,
      ExpenseShareRepository shareRepository,
      ExpenseMapper mapper,
      UserMapper userMapper
  ) {
    this.expenseRepository = expenseRepository;
    this.groupRepository = groupRepository;
    this.shareRepository = shareRepository;
    this.mapper = mapper;
    this.userMapper = userMapper;
  }

  @Override
  public GroupExpensesResponse getFromGroup(
      UUID groupId,
      String paidByName,
      BigDecimal minAmount,
      BigDecimal maxAmount
  ) {
    Group group = this.findGroupOrThrow(groupId);

    List<Expense> expenses = expenseRepository.findGroupExpenses(
        group.getId(),
        paidByName,
        minAmount,
        maxAmount
    );
    BigDecimal total = expenses
        .stream()
        .map(Expense::getAmount)
        .reduce(MoneyUtils.of(0), BigDecimal::add);

    return new GroupExpensesResponse(mapper.entitiesToResponseDtos(expenses), total);
  }

  @Override
  public GroupExpensesDetailsResponse getDetailsFromGroup(UUID groupId) {
    Group group = this.findGroupOrThrow(groupId);

    List<Expense> expenses = group
        .getExpenses()
        .stream()
        .toList(); // From Set to List for ease of use with repository & DTOs
    BigDecimal total = this.getExpensesTotal(expenses);

    // Get the group members who have unpaid shares (still owe money to other members)
    Map<User, List<ExpenseShare>> userDebts = new HashMap<>();

    for (User member : group.getMembers()) {
      List<ExpenseShare> unpaidShares = shareRepository.findAllUnpaidByUserIdAndGroupId(
          member.getId(),
          group.getId()
      );
      if (!unpaidShares.isEmpty()) {
        userDebts.put(member, unpaidShares);
      }
    }

    // Calculates how much a group member owes to each other member
    List<UserDebtsResponse> debtsResponses = userDebts.entrySet().stream().map(entry -> {
      UserReferenceDto debtor = userMapper.entityToUserDto(entry.getKey());

      // Get the amount owed (sum) to each member
      Map<UserReferenceDto, BigDecimal> owesMap = entry
          .getValue()
          .stream()
          .collect(Collectors.toMap(
              share -> userMapper.entityToUserDto(share.getExpense().getPaidBy()),
              ExpenseShare::getAmountOwed,
              BigDecimal::add
          ));

      // Transform the object above into DTO
      List<UserAmountDto> owes = owesMap
          .entrySet()
          .stream()
          .map(e -> new UserAmountDto(e.getKey(), e.getValue()))
          .toList();

      return new UserDebtsResponse(debtor, owes);
    }).toList();

    return new GroupExpensesDetailsResponse(
        total,
        mapper.entitiesToResponseDtos(expenses),
        debtsResponses
    );
  }

  @Override
  @Transactional
  public CreateExpenseResponse create(UUID groupId, CreateExpenseRequest request) {
    // Fetch group
    Group group = this.findGroupOrThrow(groupId);

    // Check that all paidForUser exist in the group
    Set<String> groupMemberNames = group
        .getMembers()
        .stream()
        .map(user -> user.getName().toLowerCase())
        .collect(Collectors.toSet());

    for (UserPaidForDto dto : request.usersPaidFor()) {
      if (!groupMemberNames.contains(dto.name().toLowerCase())) {
        throw new RuntimeException("User not found in group: " + dto.name());
      }
    }

    // Check that paidByUserId exists in the group
    User paidByUser = group
        .getMembers()
        .stream()
        .filter(user -> Objects.equals(user.getName().toLowerCase(), request.paidByName().toLowerCase()))
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("Payer not found in group: " + request.paidByName()));

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
          .filter(user -> Objects.equals(user.getName().toLowerCase(), userDto.name().toLowerCase()))
          .findFirst()
          .orElseThrow(() -> new EntityNotFoundException("Group member not found in group: " + userDto.name()));

      ExpenseShare share = new ExpenseShare();
      share.setDebtor(member);
      share.setAmountOwed(userDto.amountOwed());
      share.setIsPaid(member.getId().equals(paidByUser.getId()));

      expense.addExpenseShare(share);
    }

    // Add the expenseShares to the expense & save
    Expense savedExpense = expenseRepository.save(expense);
    return mapper.expenseToResponseDto(savedExpense);
  }

  private Group findGroupOrThrow(UUID groupId) {
    return groupRepository
        .findById(groupId)
        .orElseThrow(() -> new EntityNotFoundException("Group not found"));
  }

  private BigDecimal getExpensesTotal(List<Expense> expenses) {
    return expenses.stream().map(Expense::getAmount).reduce(MoneyUtils.of(0), BigDecimal::add);
  }
}
