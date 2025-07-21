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

@Entity
@Table(name = "user_table")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "public_name")
  private String publicName;

  @Column(name = "password")
  private String password;

  @ManyToMany(mappedBy = "participants")
  private Set<Tricount> tricounts = new HashSet<>();

  @OneToMany(mappedBy = "paidBy", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Transaction> transactions = new HashSet<>();
}
