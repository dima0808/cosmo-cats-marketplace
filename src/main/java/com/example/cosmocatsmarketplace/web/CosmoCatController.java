package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.domain.CosmoCatDetails;
import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import com.example.cosmocatsmarketplace.dto.DataWrapperDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.annotation.FeatureToggle;
import com.example.cosmocatsmarketplace.service.CosmoCatService;
import com.example.cosmocatsmarketplace.service.OrderService;
import com.example.cosmocatsmarketplace.service.mapper.GeneralServiceMapper;
import jakarta.validation.Valid;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<DataWrapperDto> getAllCosmoCats(
      @RequestParam(required = false) String include) {
    DataWrapperDto response = DataWrapperDto.builder()
        .data(cosmoCatMapper.toCosmoCatDto(cosmoCatService.getAllCosmoCats()))
        .build();
    if (include != null && include.equals("orders")) {
      HashMap<String, Object> included = new HashMap<>();
      included.put("orders", cosmoCatMapper.toOrderDto(orderService.getAllOrders()));
      response.setIncluded(included);
    }
    return ResponseEntity.ok(response);
  }

  @GetMapping("/contacts")
  public ResponseEntity<DataWrapperDto> getAllCosmoCatsContacts() {
    DataWrapperDto response = DataWrapperDto.builder()
        .data(cosmoCatService.getAllCosmoCatContacts())
        .build();
    return ResponseEntity.ok(response);
  }

  @GetMapping("{catReference}")
  public ResponseEntity<DataWrapperDto> getCosmoCatByReference(@PathVariable UUID catReference,
      @RequestParam(required = false) String include) {
    DataWrapperDto response;
    if (include != null && include.equals("orders")) {
      CosmoCatDetails cosmoCatDetails = cosmoCatService.getCosmoCatByReference(catReference, true);
      HashMap<String, Object> included = new HashMap<>();
      included.put("orders", cosmoCatMapper.toOrderDto(cosmoCatDetails.getOrders()));
      CosmoCatDto cosmoCatDto = cosmoCatMapper.toCosmoCatDto(cosmoCatDetails);
      cosmoCatDto.setOrders(orderService.getOrderNumbersByCatReference(catReference));
      response = DataWrapperDto.builder()
          .data(cosmoCatDto)
          .included(included)
          .build();
    } else {
      CosmoCatDto cosmoCatDto = cosmoCatMapper.toCosmoCatDto(
          cosmoCatService.getCosmoCatByReference(catReference));
      cosmoCatDto.setOrders(orderService.getOrderNumbersByCatReference(catReference));
      response = DataWrapperDto.builder()
          .data(cosmoCatDto)
          .build();
    }
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<DataWrapperDto> createCosmoCat(@RequestBody @Valid CosmoCatDto cosmoCatDto) {
    DataWrapperDto response = DataWrapperDto.builder()
        .data(cosmoCatMapper.toCosmoCatDto(
            cosmoCatService.saveCosmoCat(cosmoCatMapper.toCosmoCatDetails(cosmoCatDto))))
        .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("{catReference}")
  public ResponseEntity<DataWrapperDto> updateCosmoCat(@PathVariable UUID catReference,
      @RequestBody CosmoCatDto cosmoCatDto) {
    DataWrapperDto response = DataWrapperDto.builder()
        .data(cosmoCatMapper.toCosmoCatDto(
            cosmoCatService.saveCosmoCat(
                catReference, cosmoCatMapper.toCosmoCatDetails(cosmoCatDto))))
        .build();
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("{catReference}")
  public ResponseEntity<Void> deleteCosmoCat(@PathVariable UUID catReference) {
    cosmoCatService.deleteCosmoCatByReference(catReference);
    return ResponseEntity.noContent().build();
  }
}
