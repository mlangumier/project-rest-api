package fr.hb.mlang.projectrestapi.entity;

import fr.hb.mlang.projectrestapi.utils.MoneyConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * An {@link Expense} is created when a {@link User} pays for multiple other users. This
 * <code>expense</code> generates {@link ExpenseShare}s for those involved (including the user who
 * paid for it), and is linked to the {@link Group}.
 */
@Entity
@Table(name = "expense")
public class Expense implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", nullable = false, updatable = false)
  private String name;

  @Convert(converter = MoneyConverter.class)
  @Column(name = "amount", precision = 10, scale = 2, nullable = false,  updatable = false)
  private BigDecimal amount;

  @Column(name = "paid_at", nullable = false, updatable = false)
  private LocalDateTime paidAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User paidBy;

  @ManyToOne
  @JoinColumn(name = "group_id", nullable = false, updatable = false)
  private Group group;

  @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
  private Set<ExpenseShare> expenseShares = new HashSet<>();

  /**
   * Default constructor
   */
  public Expense() {
    // Required by JPA
  }

  /**
   * Entity constructor
   *
   * @param id     UUID identifier
   * @param name   Name of the expense
   * @param amount Total amount paid for this expense
   * @param paidAt Date and time when the expense was paid
   * @param paidBy Entity {@link User} who paid for the expense
   * @param group  Entity {@link Group} this expense belongs to
   */
  public Expense(
      UUID id,
      String name,
      BigDecimal amount,
      LocalDateTime paidAt,
      User paidBy,
      Group group
  ) {
    this.id = id;
    this.name = name;
    this.amount = amount;
    this.paidAt = paidAt;
    this.paidBy = paidBy;
    this.group = group;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getPaidAt() {
    return paidAt;
  }

  public void setPaidAt(LocalDateTime paidAt) {
    this.paidAt = paidAt;
  }

  public User getPaidBy() {
    return paidBy;
  }

  public void setPaidBy(User paidBy) {
    this.paidBy = paidBy;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public Set<ExpenseShare> getExpenseShares() {
    return expenseShares;
  }

  public void setExpenseShares(Set<ExpenseShare> expenseShares) {
    this.expenseShares = expenseShares;
  }

  //--- Helper methods

  public void addExpenseShare(ExpenseShare expenseShare) {
    expenseShare.setExpense(this);
    this.expenseShares.add(expenseShare);
  }

  public void removeExpenseShare(ExpenseShare expenseShare) {
    this.expenseShares.remove(expenseShare);
    expenseShare.setExpense(null);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Expense expense)) {
      return false;
    }
    return Objects.equals(getId(), expense.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "Expense{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", amount=" + amount +
        ", paidAt=" + paidAt +
        ", paidBy=" + paidBy +
        ", group=" + group +
        '}';
  }
}
