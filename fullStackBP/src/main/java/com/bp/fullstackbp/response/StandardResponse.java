package com.bp.fullstackbp.response;

import com.bp.fullstackbp.exceptions.CustomException;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class StandardResponse<T> {
  private final T data;
  private final ErrorResponse error;
  private HttpStatus status;

  public StandardResponse(ErrorResponse error) {
    this.error = error;
    this.data = null;
  }

  public StandardResponse(ErrorResponse error, HttpStatus status) {
    this.error = error;
    this.data = null;
    this.status = status;
  }

  public StandardResponse(T data) {
    this.data = data;
    this.error = null;
    this.status = HttpStatus.OK;
  }

  public StandardResponse(CustomException e) {
    this.error = e.getErrorResponse();
    this.status = e.getHttpStatus();
    this.data = null;
  }
}
