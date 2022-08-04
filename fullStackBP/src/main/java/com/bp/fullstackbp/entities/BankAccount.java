package com.bp.fullstackbp.entities;


import com.bp.fullstackbp.enumerate.AccountType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class BankAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String accountNumber;

  private AccountType accountType;

  private double initialBalance;

  private double currentBalance;

  private boolean isActive;

  @OneToMany(mappedBy = "bankAccount")
  private List<Movement> movements;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;
}
