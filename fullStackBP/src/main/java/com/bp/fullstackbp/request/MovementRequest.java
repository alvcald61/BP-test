package com.bp.fullstackbp.request;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovementRequest {
  private Integer id;

  private Long bankAccountId;

  private String transactionDate;

  private String movementType;

  private double amount;

  private double balance;

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }
}
