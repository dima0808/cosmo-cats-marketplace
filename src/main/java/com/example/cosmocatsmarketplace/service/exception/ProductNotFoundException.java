package com.example.cosmocatsmarketplace.service.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

  public ProductNotFoundException(UUID productReference) {
    super(String.format("Product with reference %s not found", productReference));
  }
}
