package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.annotation.FeatureToggle;
import com.example.cosmocatsmarketplace.service.CosmoCatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatController {

  private final CosmoCatService cosmoCatService;

  public CosmoCatController(CosmoCatService cosmoCatService) {
    this.cosmoCatService = cosmoCatService;
  }

  @GetMapping
  @FeatureToggle(FeatureToggles.COSMO_CATS)
  public ResponseEntity<List<CosmoCatDto>> getAllCosmoCats() {
    List<CosmoCatDto> cosmoCats = cosmoCatService.getCosmoCats();
    return ResponseEntity.ok(cosmoCats);
  }
}