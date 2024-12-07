package com.example.cosmocatsmarketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderEntryDto {

  private Integer quantity;
  private ProductDto product;
}
