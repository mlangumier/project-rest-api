package fr.hb.mlang.projectrestapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "settlement")
public class Settlement {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

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
}
