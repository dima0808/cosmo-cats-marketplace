package com.example.cosmocatsmarketplace.repository.mapper;

import com.example.cosmocatsmarketplace.domain.OrderDetails;
import com.example.cosmocatsmarketplace.domain.OrderEntryDetails;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntryEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProductRepositoryMapper.class})
public interface OrderRepositoryMapper {

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
}
