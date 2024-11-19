package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.dto.CosmoCatDto;

import java.util.List;

public interface CosmoCatService {
    List<CosmoCatDto> getCosmoCats();
}