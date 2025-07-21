package fr.hb.mlang.projectrestapi.entity;

import fr.hb.mlang.projectrestapi.enums.TransactionType;
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
@Table(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "title", nullable = false, updatable = false)
  private String title;

  @Column(name = "amount", nullable = false, updatable = false)
  private Double amount;

  @Column(name = "paid_at", nullable = false, updatable = false)
  private LocalDateTime paidAt;

  @Column(name = "type", nullable = false, updatable = false)
  private TransactionType type;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User paidBy;

  @ManyToOne
  @JoinColumn(name = "tricount_id", nullable = false, updatable = false)
  private Tricount tricount;

  //TODO: see if relevant to change the following two attributes:
  // - Either keep these two attributes, use one at a time for each transaction
  // - Or manage it another way (inheritance according to TransactionType?)

  // One (receiver? receptor?) (ex: remboursement)
  // Many (receivers? receptors?) (ex: paie 150€ for me + 2 other people)
}
