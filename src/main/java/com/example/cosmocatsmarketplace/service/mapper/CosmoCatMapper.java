package com.example.cosmocatsmarketplace.service.mapper;

import com.example.cosmocatsmarketplace.domain.CosmoCat;
import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CosmoCatMapper {


    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    CosmoCatDto toCosmoCatDto(CosmoCat cat);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    CosmoCat toCosmoCat(CosmoCatDto catDto);
}