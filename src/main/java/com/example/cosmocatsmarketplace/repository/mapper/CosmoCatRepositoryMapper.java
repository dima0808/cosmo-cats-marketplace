package com.example.cosmocatsmarketplace.repository.mapper;

import com.example.cosmocatsmarketplace.domain.CosmoCatDetails;
import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderRepositoryMapper.class})
public interface CosmoCatRepositoryMapper {

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
  @Mapping(target = "catReference", source = "catReference")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "address", source = "address")
  @Mapping(target = "phoneNumber", source = "phoneNumber")
  @Mapping(target = "orders", source = "orders", qualifiedByName = "toOrderEntity")
  CosmoCatEntity toCosmoCatEntity(CosmoCatDetails cosmoCatDetails);
}
