package com.example.cosmocatsmarketplace.service.mapper;

import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.domain.CosmoCatDetails;
import com.example.cosmocatsmarketplace.domain.OrderDetails;
import com.example.cosmocatsmarketplace.domain.OrderEntryDetails;
import com.example.cosmocatsmarketplace.domain.ProductDetails;
import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import com.example.cosmocatsmarketplace.dto.OrderDto;
import com.example.cosmocatsmarketplace.dto.OrderEntryDto;
import com.example.cosmocatsmarketplace.dto.ProductDto;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface GeneralServiceMapper {

  @Mapping(target = "catReference", source = "catReference")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "address", source = "address")
  @Mapping(target = "phoneNumber", source = "phoneNumber")
  @Mapping(target = "orders", source = "orders", qualifiedByName = "toOrderNumbers")
  CosmoCatDto toCosmoCatDto(CosmoCatDetails cosmoCatDetails);

  List<CosmoCatDto> toCosmoCatDto(List<CosmoCatDetails> cosmoCatDetails);

  @Named("toOrderNumbers")
  default List<UUID> toOrderNumbers(List<OrderDetails> orders) {
    if (orders == null) {
      return List.of();
    }
    return orders.stream()
        .map(OrderDetails::getOrderNumber)
        .toList();
  }

  @Mapping(target = "orderNumber", source = "orderNumber")
  @Mapping(target = "orderEntries", source = "orderEntries", qualifiedByName = "toOrderEntryDto")
  OrderDto toOrderDto(OrderDetails orderDetails);

  List<OrderDto> toOrderDto(List<OrderDetails> orderDetails);

  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "product", source = "product", qualifiedByName = "toProductDto")
  OrderEntryDto toOrderEntryDto(OrderEntryDetails orderEntryDetails);

  @Named("toOrderEntryDto")
  List<OrderEntryDto> toOrderEntryDto(List<OrderEntryDetails> orderEntryDetails);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "categories", source = "categories", qualifiedByName = "toCategoriesString")
  @Named("toProductDto")
  ProductDto toProductDto(ProductDetails productDetails);

  List<ProductDto> toProductDto(List<ProductDetails> productDetails);

  @Named("toCategoriesString")
  default List<String> toCategoriesString(List<CategoryType> categoriesType) {
    return categoriesType.stream()
        .map(CategoryType::getDisplayName)
        .toList();
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "catReference", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "address", source = "address")
  @Mapping(target = "phoneNumber", source = "phoneNumber")
  @Mapping(target = "orders", ignore = true)
  CosmoCatDetails toCosmoCatDetails(CosmoCatDto cosmoCatDto);

  List<CosmoCatDetails> toCosmoCatDetails(List<CosmoCatDto> cosmoCatDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderNumber", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "orderEntries", source = "orderEntries", qualifiedByName = "toOrderEntryDetails")
  OrderDetails toOrderDetails(OrderDto orderDto);

  List<OrderDetails> toOrderDetails(List<OrderDto> orderDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "product", source = "product", qualifiedByName = "toProductDetailsWithId")
  OrderEntryDetails toOrderEntryDetails(OrderEntryDto orderEntryDto);

  @Named("toOrderEntryDetails")
  List<OrderEntryDetails> toOrderEntryDetails(List<OrderEntryDto> orderEntryDto);

  @Mapping(target = "id", source = "id")
  @Named("toProductDetailsWithId")
  ProductDetails toProductDetailsWithId(ProductDto productDto);

  @Mapping(target = "id", ignore = true)
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
