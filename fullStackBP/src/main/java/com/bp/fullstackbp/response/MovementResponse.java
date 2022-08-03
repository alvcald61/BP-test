package com.bp.fullstackbp.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MovementResponse {
  private Integer id;

  private String transactionDate;

  private String movementType;

  private double amount;

  private double availableBalance;

  @SerializedName(value = "clientName")
  private String bankAccountClientName;

  @SerializedName("accountNumber")
  private String bankAccountAccountNumber;
}
