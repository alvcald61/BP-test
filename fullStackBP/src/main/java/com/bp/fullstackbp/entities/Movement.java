package com.bp.fullstackbp.entities;


import com.bp.fullstackbp.enumerate.MovementType;
import com.google.gson.Gson;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Movement {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Temporal(TemporalType.TIMESTAMP)
  private Date transactionDate;

  private MovementType movementType;

  private double amount;

  private double initialBalance;

  private double availableBalance;

  private boolean isActive;

  @ManyToOne
  @JoinColumn(name = "bank_account_id")
  private BankAccount bankAccount;


  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
