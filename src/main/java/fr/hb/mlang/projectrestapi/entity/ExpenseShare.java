package fr.hb.mlang.projectrestapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * An {@link ExpenseShare} represents the monetary part of an {@link Expense} that the {@link User}
 * (debtor) owes the one who paid for the <code>expense</code>.
 */
@Entity
@Table(name = "expense_share")
public class ExpenseShare {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "amount_owed", nullable = false, updatable = false)
  private Double amountOwed;

  @Column(name = "is_paid", nullable = false)
  private boolean paid;

  @ManyToOne
  @JoinColumn(name = "expense_id", nullable = false, updatable = false)
  private Expense expense;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User debtor;
}
