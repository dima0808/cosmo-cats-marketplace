package com.example.cosmocatsmarketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderEntryDetails {

  private Long id;
  private Integer quantity;
  private ProductDetails product;
}
