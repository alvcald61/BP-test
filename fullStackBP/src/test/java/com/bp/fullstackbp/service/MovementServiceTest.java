package com.bp.fullstackbp.service;

import com.bp.fullstackbp.entities.BankAccount;
import com.bp.fullstackbp.entities.Movement;
import com.bp.fullstackbp.enumerate.MovementType;
import com.bp.fullstackbp.exceptions.CustomException;
import com.bp.fullstackbp.repository.BankAccountRepository;
import com.bp.fullstackbp.repository.MovementRepository;
import com.bp.fullstackbp.request.MovementRequest;
import com.bp.fullstackbp.response.MovementResponse;
import com.bp.fullstackbp.service.impl.MovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class MovementServiceTest {

  @Mock
  private MovementRepository movementRepository;

  @Mock
  private BankAccountRepository bankAccountRepository;

  @InjectMocks
  private MovementService movementService;

  private final BankAccount bankAccount = BankAccount.builder().currentBalance(0).initialBalance(0).id(1L).build();


  @BeforeEach
  void setMockOutput() {
    when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(bankAccount));
    when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);
//    when(movementRepository.save(any(Movement.class))).then(AdditionalAnswers.returnsFirstArg());
    when(movementRepository.save(any(Movement.class))).then(answer -> {
      Movement movement = answer.getArgument(0);
      movement.setId(1L);
      return movement;
    });
  }


  @DisplayName("Testing adding a movement with initial balance iqual 0")
  @Test
  void createMovementWithFailure() {
    bankAccount.setCurrentBalance(0);
    CustomException exception = assertThrows(CustomException.class, () -> {
      MovementRequest request = MovementRequest.builder().movementType(MovementType.WITHDRAWAL.toString())
        .amount(100).bankAccountId(1L).transactionDate("10/10/2022 10:10:10").balance(0).build();
      movementService.createMovement(request);
    });
    assertEquals("Saldo no disponible", exception.getMessage());
    assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatus());
  }

  @DisplayName("Testing correct adding a movement")
  @Test
  void createMovementSuccessfully() {
    bankAccount.setCurrentBalance(100);
    MovementRequest request = MovementRequest.builder().movementType(MovementType.WITHDRAWAL.toString())
      .amount(100).bankAccountId(1L).transactionDate("10/10/2022 10:10:10").balance(0).build();
    MovementResponse response = movementService.createMovement(request);
    assertEquals(0, response.getAvailableBalance());
    assertEquals(0, bankAccount.getCurrentBalance());
    assertNotNull(response.getId());
    assertEquals(1L, response.getId().longValue());

  }
}