package com.bp.fullstackbp.service;

import com.bp.fullstackbp.request.MovementRequest;
import com.bp.fullstackbp.response.MovementResponse;

import java.util.List;

public interface IMovementService {
  List<MovementResponse> getAllMovements();

  boolean deleteMovement(Long id);

  MovementResponse createMovement(MovementRequest movement);

  MovementResponse updateMovement(MovementRequest movement);

  MovementResponse getMovement(Long id);
}
