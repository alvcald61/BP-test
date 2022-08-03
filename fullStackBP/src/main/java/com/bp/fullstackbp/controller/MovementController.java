package com.bp.fullstackbp.controller;

import com.bp.fullstackbp.exceptions.CustomException;
import com.bp.fullstackbp.request.MovementRequest;
import com.bp.fullstackbp.response.MovementResponse;
import com.bp.fullstackbp.response.StandardResponse;
import com.bp.fullstackbp.service.impl.MovementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovementController {
  private final MovementService movementService;

  public MovementController(MovementService movementService) {
    this.movementService = movementService;
  }

  @GetMapping("/listarTodos")
  public StandardResponse<List<MovementResponse>> getAllMovements() {
    try {
      return new StandardResponse<>(movementService.getAllMovements());
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }


  @PutMapping("/actualizar")
  public StandardResponse<MovementResponse> updateMovement(@RequestBody MovementRequest movementRequest) {
    try {
      return new StandardResponse<>(movementService.updateMovement(movementRequest));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @PostMapping("/guardar")
  public StandardResponse<MovementResponse> createMovement(@RequestBody MovementRequest movementRequest) {
    try {
      return new StandardResponse<>(movementService.createMovement(movementRequest));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }

  @DeleteMapping("eliminar/{id}")
  public StandardResponse<Boolean> deleteMovement(@PathVariable Long id) {
    try {
      return new StandardResponse<>(movementService.deleteMovement(id));
    } catch (CustomException e) {
      return new StandardResponse<>(e);
    }
  }
}
