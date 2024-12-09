package com.example.cosmocatsmarketplace.repository.mapper;

import com.example.cosmocatsmarketplace.domain.ProductDetails;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductRepositoryMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "categories", source = "categories")
  @Named("toProductDetails")
  ProductDetails toProductDetails(ProductEntity productEntity);

  List<ProductDetails> toProductDetails(List<ProductEntity> productEntity);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "categories", source = "categories")
  @Named("toProductEntity")
  ProductEntity toProductEntity(ProductDetails productDetails);
}
