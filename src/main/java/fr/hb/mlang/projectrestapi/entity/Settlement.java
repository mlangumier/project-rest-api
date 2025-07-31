package fr.hb.mlang.projectrestapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A {@link Settlement} represents the monetary transfer between two {@link User}s of a same
 * {@link Group}, allowing the debtor (fromUser) to reimburse the person who originally paid for the
 * {@link Expense} (toUser).
 */
@Entity
@Table(name = "settlement")
public class Settlement implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "amount", nullable = false, updatable = false)
  private Double amount;

  @Column(name = "paid_at", nullable = false, updatable = false)
  private LocalDateTime paidAt;

  @Column(name = "comment")
  private String comment;

  @ManyToOne
  @JoinColumn(name = "group_id", nullable = false, updatable = false)
  private Group group;

  @ManyToOne
  @JoinColumn(name = "from_user_id", nullable = false, updatable = false)
  private User fromUser;

  @ManyToOne
  @JoinColumn(name = "to_user_id", nullable = false, updatable = false)
  private User toUser;

  /**
   * Default constructor
   */
  public Settlement() {
    // Required by JPA
  }

  /**
   * Entity constructor
   *
   * @param id       UUID identifier
   * @param amount   Amount being transferred from one {@link User} to the other
   * @param paidAt   Date and time of the settlement
   * @param comment  Reason for the settlement (title, description, comment, etc.)
   * @param group    {@link Group} that originated the {@link Expense} and where the settlement will
   *                 happen
   * @param fromUser <code>User</code> who generates this settlement to reimburse another
   * @param toUser   <code>User</code> that receives the settlement
   */
  public Settlement(
      UUID id, Double amount, LocalDateTime paidAt, String comment, Group group,
      User fromUser, User toUser) {
    this.id = id;
    this.amount = amount;
    this.paidAt = paidAt;
    this.comment = comment;
    this.group = group;
    this.fromUser = fromUser;
    this.toUser = toUser;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public LocalDateTime getPaidAt() {
    return paidAt;
  }

  public void setPaidAt(LocalDateTime paidAt) {
    this.paidAt = paidAt;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public User getFromUser() {
    return fromUser;
  }

  public void setFromUser(User fromUser) {
    this.fromUser = fromUser;
  }

  public User getToUser() {
    return toUser;
  }

  public void setToUser(User toUser) {
    this.toUser = toUser;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Settlement that)) {
      return false;
    }
    return Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "Settlement{" +
        "id='" + id + '\'' +
        ", amount=" + amount +
        ", paidAt=" + paidAt +
        ", comment='" + comment + '\'' +
        ", group=" + group +
        ", fromUser=" + fromUser +
        ", toUser=" + toUser +
        '}';
  }
}
