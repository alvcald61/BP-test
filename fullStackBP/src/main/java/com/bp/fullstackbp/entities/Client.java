package com.bp.fullstackbp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "clientId")
public class Client extends Person {

  private String password;

  private boolean isActive;

  @OneToMany(mappedBy = "client")
  private List<BankAccount> bankAccounts;
}

