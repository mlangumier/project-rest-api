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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tricount")
public class Tricount {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToMany
  @JoinTable(name = "user_tricount", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tricount_id"))
  private Set<User> participants = new HashSet<>();

  @OneToMany(mappedBy = "tricount", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Transaction> transactions = new HashSet<>();
}
