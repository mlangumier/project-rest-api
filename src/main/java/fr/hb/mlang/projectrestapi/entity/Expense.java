package fr.hb.mlang.projectrestapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * An {@link Expense} is created when a {@link User} pays for multiple other users. This
 * <code>expense</code> generates {@link ExpenseShare}s for those involved (including the user who
 * paid for it), and is linked to the {@link Group}.
 */
@Entity
@Table(name = "expense")
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "name", nullable = false, updatable = false)
  private String name;

  @Column(name = "amount", nullable = false, updatable = false)
  private Double amount;

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

}
