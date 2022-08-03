package com.bp.fullstackbp.enumerate;

import lombok.Getter;

@Getter
public enum MovementType {
  WITHDRAWAL("Retiro"), DEPOSIT("Depósito");
  private final String altName;

  MovementType(String altName) {
    this.altName = altName;
  }
}
