package com.example.cosmocatsmarketplace.service.mapper;

import com.example.cosmocatsmarketplace.domain.CosmoCatDetails;
import com.example.cosmocatsmarketplace.domain.OrderDetails;
import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import com.example.cosmocatsmarketplace.dto.CosmoCatListDto;
import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContacts;
import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContactsList;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CosmoCatServiceMapper {

  @Mapping(target = "catReference", source = "catReference")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "address", source = "address")
  @Mapping(target = "phoneNumber", source = "phoneNumber")
  @Mapping(target = "orders", source = "orders", qualifiedByName = "toOrderNumbers")
  CosmoCatDto toCosmoCatDto(CosmoCatDetails cosmoCatDetails);

  List<CosmoCatDto> toCosmoCatDto(List<CosmoCatDetails> cosmoCatDetails);

  default CosmoCatListDto toCosmoCatListDto(List<CosmoCatDetails> cosmoCatDetails) {
    return CosmoCatListDto.builder()
        .cosmoCats(toCosmoCatDto(cosmoCatDetails))
        .build();
  }

  @Named("toOrderNumbers")
  default List<UUID> toOrderNumbers(List<OrderDetails> orders) {
    if (orders == null) {
      return List.of();
    }
    return orders.stream()
        .map(OrderDetails::getOrderNumber)
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

  default CosmoCatContactsList toCosmoCatContactsList(List<CosmoCatContacts> contacts) {
    return CosmoCatContactsList.builder()
        .contacts(contacts)
        .build();
  }
}
