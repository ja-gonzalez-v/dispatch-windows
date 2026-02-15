package com.dispatchwindows.web.error;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> notFound(NotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body("NOT_FOUND", e.getMessage()));
  }

  @ExceptionHandler({SlotSoldOutException.class, SlotLockedException.class})
  public ResponseEntity<?> conflict(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body("CONFLICT", e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> generic(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body("ERROR", "Unexpected error"));
  }

  private Map<String, Object> body(String code, String message) {
    return Map.of(
      "timestamp", Instant.now().toString(),
      "code", code,
      "message", message
    );
  }
}
