package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.dto.DataWrapperDto;
import com.example.cosmocatsmarketplace.dto.ProductDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.annotation.FeatureToggle;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.mapper.GeneralServiceMapper;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final GeneralServiceMapper productMapper;

  @GetMapping
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<DataWrapperDto> getAllProducts() {
    DataWrapperDto response = DataWrapperDto.builder()
        .data(productMapper.toProductDto(productService.getAllProducts()))
        .build();
    return ResponseEntity.ok(response);
  }

  @GetMapping("{productId}")
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<DataWrapperDto> getProductById(@PathVariable Long productId) {
    DataWrapperDto response = DataWrapperDto.builder()
        .data(productMapper.toProductDto(productService.getProductById(productId)))
        .build();
    return ResponseEntity.ok(response);
  }

  @PostMapping
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<DataWrapperDto> createProduct(@RequestBody @Valid ProductDto productDto) {
    DataWrapperDto response = DataWrapperDto.builder()
        .data(productMapper.toProductDto(
            productService.saveProduct(productMapper.toProductDetails(productDto))))
        .build();
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("{productId}")
  public ResponseEntity<DataWrapperDto> updateProduct(@PathVariable Long productId,
      @RequestBody ProductDto productDto) {
    DataWrapperDto response = DataWrapperDto.builder()
        .data(productMapper.toProductDto(
            productService.saveProduct(productId, productMapper.toProductDetails(productDto))))
        .build();
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
    productService.deleteProductById(productId);
    return ResponseEntity.noContent().build();
  }
}
