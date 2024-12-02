package com.example.cosmocatsmarketplace.service.exception;

import java.util.UUID;

public class CosmoCatNotFoundException extends RuntimeException {

  public CosmoCatNotFoundException(UUID catReference) {
    super(String.format("Cosmocat with reference '%s' not found", catReference));
  }

  public CosmoCatNotFoundException(String email) {
    super(String.format("Cosmocat with email '%s' not found", email));
  }
}
