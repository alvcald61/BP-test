package com.bp.fullstackbp.service.impl;

import com.bp.fullstackbp.entities.BankAccount;
import com.bp.fullstackbp.entities.Movement;
import com.bp.fullstackbp.enumerate.MovementType;
import com.bp.fullstackbp.exceptions.CustomException;
import com.bp.fullstackbp.repository.BankAccountRepository;
import com.bp.fullstackbp.repository.MovementRepository;
import com.bp.fullstackbp.request.MovementRequest;
import com.bp.fullstackbp.response.MovementResponse;
import com.bp.fullstackbp.service.IMovementService;
import com.bp.fullstackbp.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovementService implements IMovementService {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final MovementRepository movementRepository;

  private final BankAccountRepository bankAccountRepository;


  public MovementService(MovementRepository movementRepository, BankAccountRepository bankAccountRepository) {
    this.movementRepository = movementRepository;
    this.bankAccountRepository = bankAccountRepository;
  }

  @Override
  public List<MovementResponse> getAllMovements() {
    try {
      return movementRepository
        .findAllByIsActiveIsTrue()
        .stream()
        .map(objectMapper::mapMovementResponse)
        .collect(Collectors.toList());
    } catch (Exception e) {
      throw new CustomException("Error al listar los movimientos", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public boolean deleteMovement(Long id) {
    try {
      Movement movement = movementRepository.findById(id)
        .orElseThrow(() -> new CustomException("Movement not found", HttpStatus.NOT_FOUND));
      movement.setActive(false);
      movementRepository.save(movement);
      return true;
    } catch (CustomException e) {
      throw e;
    } catch (Exception e) {
      throw new CustomException("Error al eliminar el movimiento", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public MovementResponse createMovement(MovementRequest movement) {
    try {
      Movement movementEntity = objectMapper.map(movement);
      BankAccount account = bankAccountRepository.findById(movement.getBankAccountId())
        .orElseThrow(() -> new CustomException("BankAccount not found", HttpStatus.NOT_FOUND));
      movementEntity.setBankAccount(account);
      if (account.getCurrentBalance() == 0 && movementEntity.getMovementType() == MovementType.WITHDRAWAL) {
        throw new CustomException("Saldo no disponible", HttpStatus.FORBIDDEN);
      }
      movementEntity.setInitialBalance(account.getCurrentBalance());
      double balance = account.getCurrentBalance() -
        (movementEntity.getMovementType().equals(MovementType.WITHDRAWAL) ? movementEntity.getAmount() : -movementEntity.getAmount());
      movementEntity.setAvailableBalance(balance);
      account.setCurrentBalance(balance);
      movementEntity = movementRepository.save(movementEntity);
      bankAccountRepository.save(account);
      return objectMapper.mapMovementResponse(movementEntity);
    } catch (CustomException e) {
      throw e;
    } catch (ParseException e) {
      throw new CustomException("Formato de fecha incorrecto, debe ser: dd/MM/yyyy HH:mm:ss o dd/MM/yyyy", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public MovementResponse updateMovement(MovementRequest movement) {
    try {
      Movement movementEntity = objectMapper.map(movement);
      movementEntity = movementRepository.save(movementEntity);
      return objectMapper.mapMovementResponse(movementEntity);
    } catch (ParseException e) {
      throw new CustomException("Formato de fecha incorrecto, debe ser: dd/MM/yyyy HH:mm:ss", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      throw new CustomException("Error al actualizar el movimiento", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public MovementResponse getMovement(Long id) {
    if (id == null) {
      throw new CustomException("Id no puede ser nulo", HttpStatus.BAD_REQUEST);
    }
    Optional<Movement> movement = movementRepository.findById(id);
    if (movement.isEmpty()) {
      throw new CustomException("El movimiento no se encontró", HttpStatus.NOT_FOUND);
    }
    return objectMapper.mapMovementResponse(movement.get());
  }

}

