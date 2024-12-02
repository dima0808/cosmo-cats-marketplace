package com.example.cosmocatsmarketplace.domain;

import com.example.cosmocatsmarketplace.common.CategoryType;
import java.util.List;
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
  private String name;
  private String description;
  private Integer price;
  private List<CategoryType> categories;
}
