package com.bp.fullstackbp.response;

import lombok.Data;

@Data
public class BankAccountResponse {
  private Long id;

  private String accountNumber;

  private String accountType;

  private double initialBalance;

  private double currentBalance;

  private String clientName;

  private boolean isActive;
}
