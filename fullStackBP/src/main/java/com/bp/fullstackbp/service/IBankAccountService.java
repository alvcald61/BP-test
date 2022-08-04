package com.bp.fullstackbp.service;

import com.bp.fullstackbp.request.BankAccountRequest;
import com.bp.fullstackbp.response.BankAccountResponse;

import java.util.List;

public interface IBankAccountService {
  BankAccountResponse saveBankAccount(BankAccountRequest bankAccountRequest);

  List<BankAccountResponse> getAllBankAccounts();

  boolean deleteBankAccount(Long id);

  BankAccountResponse updateBankAccount(BankAccountRequest bankAccountRequest);

  List<BankAccountResponse> getMovement(Long id);

  BankAccountResponse getBankAccountById(Long id);
}
