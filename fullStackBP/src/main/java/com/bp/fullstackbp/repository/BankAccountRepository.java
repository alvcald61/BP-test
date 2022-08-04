package com.bp.fullstackbp.repository;

import com.bp.fullstackbp.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
  List<BankAccount> findAllByIsActiveIsTrue();
}
