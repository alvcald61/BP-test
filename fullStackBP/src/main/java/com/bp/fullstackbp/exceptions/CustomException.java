package com.bp.fullstackbp.exceptions;

import com.bp.fullstackbp.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
  private final ErrorResponse errorResponse;
  
  private final HttpStatus httpStatus;
  public CustomException(String message, HttpStatus httpStatus) {
    super(message);
    this.errorResponse = new ErrorResponse(message);
    this.httpStatus = httpStatus;
  }
  
}
