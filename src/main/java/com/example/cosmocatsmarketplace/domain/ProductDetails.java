package com.example.cosmocatsmarketplace.domain;

import com.example.cosmocatsmarketplace.common.CategoryType;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDetails {

  private Long id;
  private UUID productReference;
  private String name;
  private String description;
  private Integer price;
  private List<CategoryType> categories;
}
