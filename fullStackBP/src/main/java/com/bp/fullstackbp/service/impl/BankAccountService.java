package com.bp.fullstackbp.service.impl;

import com.bp.fullstackbp.entities.BankAccount;
import com.bp.fullstackbp.entities.Client;
import com.bp.fullstackbp.exceptions.CustomException;
import com.bp.fullstackbp.repository.BankAccountRepository;
import com.bp.fullstackbp.repository.ClientRepository;
import com.bp.fullstackbp.request.BankAccountRequest;
import com.bp.fullstackbp.response.BankAccountResponse;
import com.bp.fullstackbp.service.IBankAccountService;
import com.bp.fullstackbp.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankAccountService implements IBankAccountService {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final BankAccountRepository bankAccountRepository;

  private final ClientRepository clientRepository;

  public BankAccountService(BankAccountRepository bankAccountRepository, ClientRepository clientRepository) {
    this.bankAccountRepository = bankAccountRepository;
    this.clientRepository = clientRepository;
  }

  @Override
  public BankAccountResponse saveBankAccount(BankAccountRequest bankAccountRequest) {
    try {
      bankAccountRequest.setActive(true);
      Client client = clientRepository.findById(bankAccountRequest.getClientId())
        .orElseThrow(() -> new CustomException("Client not found", HttpStatus.NOT_FOUND));
      BankAccount accountEntity = objectMapper.map(bankAccountRequest);
      accountEntity.setClient(client);
      accountEntity.setCurrentBalance(accountEntity.getInitialBalance());
      accountEntity = bankAccountRepository.save(accountEntity);
      return objectMapper.mapBankAccountResponse(accountEntity);
    } catch (CustomException e) {
      throw e;
    } catch (Exception e) {
      throw new CustomException("Error al crear el movimiento", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public List<BankAccountResponse> getAllBankAccounts() {
    try {
      return bankAccountRepository
        .findAllByIsActiveIsTrue()
        .stream()
        .map(objectMapper::mapBankAccountResponse)
        .collect(Collectors.toList());
    } catch (Exception e) {
      throw new CustomException("Error al listar los movimientos", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public boolean deleteBankAccount(Long id) {
    try {
      BankAccount account = bankAccountRepository.findById(id)
        .orElseThrow(() -> new CustomException("No se encuentra la cuenta bancaria", HttpStatus.NOT_FOUND));
      account.setActive(false);
      bankAccountRepository.save(account);
      return true;
    } catch (CustomException e) {
      throw e;
    } catch (Exception e) {
      throw new CustomException("Error al eliminar el movimiento", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public BankAccountResponse updateBankAccount(BankAccountRequest bankAccountRequest) {
    try {
      Optional<BankAccount> account = bankAccountRepository.findById(bankAccountRequest.getId());
      if (account.isPresent()) {
        BankAccount accountEntity = objectMapper.map(bankAccountRequest);
        accountEntity = bankAccountRepository.save(accountEntity);
        return objectMapper.mapBankAccountResponse(accountEntity);
      }
    } catch (Exception e) {
      throw new CustomException("Error al crear el movimiento", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    throw new CustomException("No se encontro el movimiento", HttpStatus.NOT_FOUND);
  }

  @Override
  public List<BankAccountResponse> getMovement(Long id) {
    if (id == null) {
      throw new CustomException("Id de cliente no enviado", HttpStatus.BAD_REQUEST);
    }
    Optional<Client> optClient = clientRepository.findById(id);
    if (optClient.isEmpty()) {
      throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
    }
    Client client = optClient.get();
    if (client.getBankAccounts() != null) {
      return client.getBankAccounts().stream().map(objectMapper::mapBankAccountResponse).collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  @Override
  public BankAccountResponse getBankAccountById(Long id) {
    if (id == null) {
      throw new CustomException("Id de la cuenta no enviado", HttpStatus.BAD_REQUEST);
    }
    Optional<BankAccount> optAccount = bankAccountRepository.findById(id);
    if (optAccount.isEmpty()) {
      throw new CustomException("Cuenta bancaria no encontrada", HttpStatus.NOT_FOUND);
    }
    BankAccount account = optAccount.get();
    return objectMapper.mapBankAccountResponse(account);
  }

}
