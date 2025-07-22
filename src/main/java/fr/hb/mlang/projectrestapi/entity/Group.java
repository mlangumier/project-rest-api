package fr.hb.mlang.projectrestapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Tricount {@link Group} created by a {@link User} (owner). Will contain multiple
 * <code>Users</code> (participants) who will be able to create {@link Expense}s (= pay for
 * multiple people, which generates {@link ExpenseShare}), and will list {@link Settlement}s (users
 * reimbursing those who made <code>expenses</code>).
 */
@Entity
@Table(name = "group_table")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User owner;

  @ManyToMany
  @JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<User> participants = new HashSet<>();

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
  private Set<Expense> expenses = new HashSet<>();

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
  private Set<Settlement> settlements = new HashSet<>();
}
