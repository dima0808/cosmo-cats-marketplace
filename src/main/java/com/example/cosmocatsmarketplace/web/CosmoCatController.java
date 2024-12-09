package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import com.example.cosmocatsmarketplace.dto.CosmoCatListDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.annotation.FeatureToggle;
import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContactsList;
import com.example.cosmocatsmarketplace.service.CosmoCatService;
import com.example.cosmocatsmarketplace.service.OrderService;
import com.example.cosmocatsmarketplace.service.mapper.GeneralServiceMapper;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cosmo-cats")
@RequiredArgsConstructor
public class CosmoCatController {

  private final CosmoCatService cosmoCatService;
  private final OrderService orderService;
  private final GeneralServiceMapper cosmoCatMapper;

  @GetMapping
  @FeatureToggle(FeatureToggles.COSMO_CATS)
  public ResponseEntity<CosmoCatListDto> getAllCosmoCats() {
    return ResponseEntity.ok(cosmoCatMapper.toCosmoCatListDto(cosmoCatService.getAllCosmoCats()));
  }

  @GetMapping("/contacts")
  public ResponseEntity<CosmoCatContactsList> getAllCosmoCatsContacts() {
    return ResponseEntity.ok(cosmoCatMapper.toCosmoCatContactsList(
        cosmoCatService.getAllCosmoCatContacts()));
  }

  @GetMapping("{catReference}")
  public ResponseEntity<CosmoCatDto> getCosmoCatByReference(@PathVariable UUID catReference) {
    CosmoCatDto cosmoCat = cosmoCatMapper.toCosmoCatDto(
        cosmoCatService.getCosmoCatByReference(catReference, true));
    cosmoCat.setOrders(orderService.getOrderNumbersByCatReference(catReference));
    return ResponseEntity.ok(cosmoCat);
  }

  @PostMapping
  public ResponseEntity<CosmoCatDto> createCosmoCat(@RequestBody @Valid CosmoCatDto cosmoCatDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(cosmoCatMapper.toCosmoCatDto(
        cosmoCatService.saveCosmoCat(cosmoCatMapper.toCosmoCatDetails(cosmoCatDto))));
  }

  @PutMapping("{catReference}")
  public ResponseEntity<CosmoCatDto> updateCosmoCat(@PathVariable UUID catReference,
      @RequestBody CosmoCatDto cosmoCatDto) {
    return ResponseEntity.ok(cosmoCatMapper.toCosmoCatDto(
        cosmoCatService.saveCosmoCat(catReference, cosmoCatMapper.toCosmoCatDetails(cosmoCatDto))));
  }

  @DeleteMapping("{catReference}")
  public ResponseEntity<Void> deleteCosmoCat(@PathVariable UUID catReference) {
    cosmoCatService.deleteCosmoCatByReference(catReference);
    return ResponseEntity.noContent().build();
  }
}
