package com.example.cosmocatsmarketplace.service.mapper;

import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.domain.ProductDetails;
import com.example.cosmocatsmarketplace.dto.ProductDto;
import com.example.cosmocatsmarketplace.dto.ProductListDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductServiceMapper {

  @Mapping(target = "name", source = "name")
  @Mapping(target = "productReference", source = "productReference")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategoriesString")
  @Named("toProductDto")
  ProductDto toProductDto(ProductDetails productDetails);

  List<ProductDto> toProductDto(List<ProductDetails> productDetails);

  default ProductListDto toProductListDto(List<ProductDetails> productDetails) {
    return ProductListDto.builder()
        .products(toProductDto(productDetails))
        .build();
  }

  @Named("toCategoriesString")
  default List<String> toCategoriesString(List<CategoryType> categoriesType) {
    return categoriesType.stream()
        .map(CategoryType::getDisplayName)
        .toList();
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "productReference", source = "productReference")
  @Mapping(target = "name", ignore = true)
  @Mapping(target = "description", ignore = true)
  @Mapping(target = "price", ignore = true)
  @Mapping(target = "categories", ignore = true)
  @Named("toProductDetailsWithReference")
  ProductDetails toProductDetailsWithReference(ProductDto productDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "productReference", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategoriesType")
  ProductDetails toProductDetails(ProductDto productDto);

  List<ProductDetails> toProductDetails(List<ProductDto> productDto);

  @Named("toCategoriesType")
  default List<CategoryType> toCategoriesType(List<String> categoriesString) {
    return categoriesString.stream()
        .map((categoryString) -> CategoryType.valueOf(categoryString.toUpperCase()))
        .toList();
  }
}
