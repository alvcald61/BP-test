package com.bp.fullstackbp.request;


import lombok.Data;

@Data
public class BankAccountRequest {

  private Long id;

  private Long clientId;

  private String accountNumber;

  private String accountType;

  private double initialBalance;

  private boolean isActive;
}
