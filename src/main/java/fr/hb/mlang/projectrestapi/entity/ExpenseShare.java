package fr.hb.mlang.projectrestapi.entity;

import fr.hb.mlang.projectrestapi.utils.MoneyConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * An {@link ExpenseShare} represents the monetary part of an {@link Expense} that the {@link User}
 * (debtor) owes the one who paid for the <code>expense</code>.
 */
@Entity
@Table(name = "expense_share")
public class ExpenseShare implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Convert(converter = MoneyConverter.class)
  @Column(name = "amount_owed", precision = 10, scale = 2, nullable = false, updatable = false)
  private BigDecimal amountOwed;

  @Column(name = "is_paid", nullable = false)
  private boolean isPaid;

  @ManyToOne
  @JoinColumn(name = "expense_id", nullable = false, updatable = false)
  private Expense expense;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User debtor;

  /**
   * Default constructor
   */
  public ExpenseShare() {
    // Required by JPA
  }

  /**
   * Entity constructor
   *
   * @param id         UUID identifier
   * @param amountOwed Amount owed regarding the {@link Expense}
   * @param isPaid       Boolean that specifies if the share has been reimbursed or not
   * @param expense    <code>Expense</code> that generated this share
   * @param debtor     {@link User} who owes the <code>share</code>
   */
  public ExpenseShare(UUID id, BigDecimal amountOwed, boolean isPaid, Expense expense, User debtor) {
    this.id = id;
    this.amountOwed = amountOwed;
    this.isPaid = isPaid;
    this.expense = expense;
    this.debtor = debtor;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BigDecimal getAmountOwed() {
    return amountOwed;
  }

  public void setAmountOwed(BigDecimal amountOwed) {
    this.amountOwed = amountOwed;
  }

  public boolean getIsPaid() {
    return isPaid;
  }

  public void setIsPaid(boolean paid) {
    this.isPaid = paid;
  }

  public Expense getExpense() {
    return expense;
  }

  public void setExpense(Expense expense) {
    this.expense = expense;
  }

  public User getDebtor() {
    return debtor;
  }

  public void setDebtor(User debtor) {
    this.debtor = debtor;
  }

  //INFO: equals & hashCode here also check that the ID isn't null (Using Set<> compares items with this method and remove non-persisted items (no ID))
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ExpenseShare that)) {
      return false;
    }

    if (getId() != null && that.getId() != null) {
      return Objects.equals(getId(), that.getId());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return id != null ? Objects.hashCode(getId()) : System.identityHashCode(this);
  }

  @Override
  public String toString() {
    return "ExpenseShare{" +
        "id='" + id + '\'' +
        ", amountOwed=" + amountOwed +
        ", paid=" + isPaid +
        ", expense=" + expense.getName() +
        ", debtor=" + debtor.getName() +
        '}';
  }
}
