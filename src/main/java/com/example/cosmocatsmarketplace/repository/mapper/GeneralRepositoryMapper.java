package com.example.cosmocatsmarketplace.repository.mapper;

import com.example.cosmocatsmarketplace.domain.CosmoCatDetails;
import com.example.cosmocatsmarketplace.domain.OrderDetails;
import com.example.cosmocatsmarketplace.domain.OrderEntryDetails;
import com.example.cosmocatsmarketplace.domain.ProductDetails;
import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntryEntity;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface GeneralRepositoryMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "catReference", source = "catReference")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "address", source = "address")
  @Mapping(target = "phoneNumber", source = "phoneNumber")
  @Mapping(target = "orders", source = "orders", qualifiedByName = "toOrderDetails")
  CosmoCatDetails toCosmoCatDetails(CosmoCatEntity cosmoCatEntity);

  List<CosmoCatDetails> toCosmoCatDetails(List<CosmoCatEntity> cosmoCatEntity);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "orderNumber", source = "orderNumber")
  @Mapping(target = "orderEntries", source = "orderEntries", qualifiedByName = "toOrderEntryDetails")
  OrderDetails toOrderDetails(OrderEntity orderEntity);

  @Named("toOrderDetails")
  List<OrderDetails> toOrderDetails(List<OrderEntity> orderEntity);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "product", source = "product", qualifiedByName = "toProductDetails")
  OrderEntryDetails toOrderEntryDetails(OrderEntryEntity orderEntryEntity);

  @Named("toOrderEntryDetails")
  List<OrderEntryDetails> toOrderEntryDetails(List<OrderEntryEntity> orderEntryEntity);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "categories", source = "categories")
  @Named("toProductDetails")
  ProductDetails toProductDetails(ProductEntity productEntity);

  List<ProductDetails> toProductDetails(List<ProductEntity> productEntity);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "catReference", source = "catReference")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "address", source = "address")
  @Mapping(target = "phoneNumber", source = "phoneNumber")
  @Mapping(target = "orders", source = "orders", qualifiedByName = "toOrderEntity")
  CosmoCatEntity toCosmoCatEntity(CosmoCatDetails cosmoCatDetails);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "orderNumber", source = "orderNumber")
  @Mapping(target = "orderEntries", source = "orderEntries", qualifiedByName = "toOrderEntryEntity")
  OrderEntity toOrderEntity(OrderDetails orderDetails);

  @Named("toOrderEntity")
  List<OrderEntity> toOrderEntity(List<OrderDetails> orderDetails);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "product", source = "product", qualifiedByName = "toProductEntity")
  OrderEntryEntity toOrderEntryEntity(OrderEntryDetails orderEntryDetails);

  @Named("toOrderEntryEntity")
  List<OrderEntryEntity> toOrderEntryEntity(List<OrderEntryDetails> orderEntryDetails);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "categories", source = "categories")
  @Named("toProductEntity")
  ProductEntity toProductEntity(ProductDetails productDetails);
}
