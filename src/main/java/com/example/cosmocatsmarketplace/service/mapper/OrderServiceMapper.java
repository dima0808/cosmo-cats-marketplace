package com.example.cosmocatsmarketplace.service.mapper;

import com.example.cosmocatsmarketplace.domain.OrderDetails;
import com.example.cosmocatsmarketplace.domain.OrderEntryDetails;
import com.example.cosmocatsmarketplace.dto.OrderDto;
import com.example.cosmocatsmarketplace.dto.OrderEntryDto;
import com.example.cosmocatsmarketplace.dto.OrderListDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProductServiceMapper.class})
public interface OrderServiceMapper {

  @Mapping(target = "orderNumber", source = "orderNumber")
  @Mapping(target = "orderEntries", source = "orderEntries", qualifiedByName = "toOrderEntryDto")
  OrderDto toOrderDto(OrderDetails orderDetails);

  List<OrderDto> toOrderDto(List<OrderDetails> orderDetails);

  default OrderListDto toOrderListDto(List<OrderDetails> orderDetails) {
    return OrderListDto.builder()
        .orders(toOrderDto(orderDetails))
        .build();
  }

  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "product", source = "product", qualifiedByName = "toProductDto")
  OrderEntryDto toOrderEntryDto(OrderEntryDetails orderEntryDetails);

  @Named("toOrderEntryDto")
  List<OrderEntryDto> toOrderEntryDto(List<OrderEntryDetails> orderEntryDetails);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderNumber", expression = "java(java.util.UUID.randomUUID())")
  @Mapping(target = "orderEntries", source = "orderEntries", qualifiedByName = "toOrderEntryDetails")
  OrderDetails toOrderDetails(OrderDto orderDto);

  List<OrderDetails> toOrderDetails(List<OrderDto> orderDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "product", source = "product", qualifiedByName = "toProductDetailsWithReference")
  OrderEntryDetails toOrderEntryDetails(OrderEntryDto orderEntryDto);

  @Named("toOrderEntryDetails")
  List<OrderEntryDetails> toOrderEntryDetails(List<OrderEntryDto> orderEntryDto);
}
