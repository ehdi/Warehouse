package com.ika.warehouseproduct.service.dto;

import org.springframework.http.HttpStatus;

public class ResponseMessageDTO {

  private HttpStatus status;
  private String message;

  public ResponseMessageDTO(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public ResponseMessageDTO setStatus(HttpStatus status) {
    this.status = status;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public ResponseMessageDTO setMessage(String message) {
    this.message = message;
    return this;
  }
}
