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
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
  private UUID id;

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

  /**
   * Default constructor
   */
  public Group() {
    // Required by JPA
  }

  /**
   * Entity constructor
   *
   * @param id    UUID identifier
   * @param name  Name of the group
   * @param owner Entity {@link User} who created the group
   */
  public Group(UUID id, String name, User owner) {
    this.id = id;
    this.name = name;
    this.owner = owner;
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

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public Set<User> getParticipants() {
    return participants;
  }

  public void setParticipants(Set<User> participants) {
    this.participants = participants;
  }

  public Set<Expense> getExpenses() {
    return expenses;
  }

  public void setExpenses(Set<Expense> expenses) {
    this.expenses = expenses;
  }

  public Set<Settlement> getSettlements() {
    return settlements;
  }

  public void setSettlements(Set<Settlement> settlements) {
    this.settlements = settlements;
  }

  //--- Helper methods

  public void addParticipant(User participant) {
    participants.add(participant);
  }

  public void removeParticipant(User participant) {
    participants.remove(participant);
  }

  public void addExpense(Expense expense) {
    expense.setGroup(this);
    this.expenses.add(expense);
  }

  public void removeExpense(Expense expense) {
    this.expenses.remove(expense);
    expense.setGroup(null);
  }

  public void addSettlement(Settlement settlement) {
    settlement.setGroup(this);
    this.settlements.add(settlement);
  }

  public void removeSettlement(Settlement settlement) {
    this.settlements.remove(settlement);
    settlement.setGroup(null);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Group group)) {
      return false;
    }
    return Objects.equals(getId(), group.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "Group{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", owner=" + owner +
        '}';
  }
}
