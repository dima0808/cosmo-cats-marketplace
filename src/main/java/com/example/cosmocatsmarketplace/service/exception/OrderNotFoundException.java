package com.example.cosmocatsmarketplace.service.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(Long id) {
    super(String.format("Order with id %s not found", id));
  }

  public OrderNotFoundException(UUID orderNumber) {
    super(String.format("Order with No.%s not found", orderNumber));
  }
}
