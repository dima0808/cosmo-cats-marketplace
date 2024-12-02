package com.example.cosmocatsmarketplace.dto;

import com.example.cosmocatsmarketplace.validation.ValidCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  @Size(max = 100, message = "Name cannot exceed 100 characters")
  private String name;

  @NotBlank(message = "Description is mandatory")
  @Size(max = 100, message = "Name cannot exceed 100 characters")
  private String description;

  @NotNull(message = "Price is mandatory")
  @Min(value = 1, message = "Price cannot be 0 or less")
  private Integer price;

  @NotEmpty(message = "Categories cannot be empty")
  @ValidCategory
  private List<String> categories;
}
