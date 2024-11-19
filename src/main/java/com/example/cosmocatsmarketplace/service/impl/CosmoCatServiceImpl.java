package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import com.example.cosmocatsmarketplace.service.CosmoCatService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosmoCatServiceImpl implements CosmoCatService {

  @Override
  public List<CosmoCatDto> getCosmoCats() {
    return List.of(
        CosmoCatDto.builder()
            .name("Nebulion")
            .email("nebull2024@gmail.com")
            .build(),

        CosmoCatDto.builder()
            .name("Galaxia")
            .email("galaxiakitty@gmail.com")
            .build()
    );
  }
}