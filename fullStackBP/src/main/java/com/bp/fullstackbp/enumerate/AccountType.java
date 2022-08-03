package com.bp.fullstackbp.enumerate;

import lombok.Getter;

@Getter
public enum AccountType {
  CURRENT_ACCOUNT("Cuenta Corriente"), SAVINGS_ACCOUNT("Cuenta de Ahorros");
  private final String altName;

  AccountType(String altName) {
    this.altName = altName;
  }

}
