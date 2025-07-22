package fr.hb.mlang.projectrestapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link User} of the application. Can create or participate in {@link Group}s, pay
 * {@link Expense}s or accumulate expenses debts by getting from other users {@link ExpenseShare}s,
 * and pay {@link Settlement} to other users or receive some from them.
 */
@Entity
@Table(name = "user_table")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private Set<Group> ownedGroups = new HashSet<>();

  @ManyToMany(mappedBy = "participants")
  private Set<Group> groups = new HashSet<>();

  @OneToMany(mappedBy = "paidBy", cascade = CascadeType.ALL)
  private Set<Expense> expenses = new HashSet<>();

  @OneToMany(mappedBy = "debtor", cascade = CascadeType.ALL)
  private Set<ExpenseShare> expensesShares = new HashSet<>();

  @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
  private Set<Settlement> settlementsPaid = new HashSet<>();

  @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
  private Set<Settlement> settlementsReceived = new HashSet<>();


}
