package com.example.cosmocatsmarketplace.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CosmoCat {

  Long id;
  String name;
  String email;
}
