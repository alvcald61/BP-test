package com.bp.fullstackbp.response;

import com.bp.fullstackbp.entities.Movement;
import com.bp.fullstackbp.enumerate.MovementType;
import com.bp.fullstackbp.utils.DateUtil;
import com.google.gson.Gson;
import lombok.Data;

@Data
public class BankAccountReport {
  private final String transactionDate;
  private final String client;
  private final String accountNumber;
  private final String accountType;
  private final double amount;
  private final double initialBalance;
  private final double availableBalance;
  private final boolean status;

  public BankAccountReport(Movement movement) {
    this.transactionDate = DateUtil.formatDate(movement.getTransactionDate());
    this.client = movement.getBankAccount().getClient().getName();
    this.accountNumber = movement.getBankAccount().getAccountNumber();
    this.accountType = movement.getBankAccount().getAccountType().getAltName();
    this.amount = movement.getMovementType().equals(MovementType.WITHDRAWAL) ? -movement.getAmount() : movement.getAmount();
    this.initialBalance = movement.getInitialBalance();
    this.availableBalance = movement.getAvailableBalance();
    this.status = movement.getBankAccount().isActive();
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
