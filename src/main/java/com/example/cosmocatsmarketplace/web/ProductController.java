package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.dto.ProductDto;
import com.example.cosmocatsmarketplace.dto.ProductListDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.annotation.FeatureToggle;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.mapper.ProductServiceMapper;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final ProductServiceMapper productServiceMapper;

  @GetMapping
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<ProductListDto> getAllProducts() {
    return ResponseEntity.ok(productServiceMapper.toProductListDto(productService.getAllProducts()));
  }

  @GetMapping("{productReference}")
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<ProductDto> getProductById(@PathVariable UUID productReference) {
    return ResponseEntity.ok(productServiceMapper.toProductDto(
        productService.getProductByReference(productReference)));
  }

  @PostMapping
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productServiceMapper.toProductDto(
        productService.saveProduct(productServiceMapper.toProductDetails(productDto))));
  }

  @PutMapping("{productReference}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID productReference,
      @RequestBody ProductDto productDto) {
    return ResponseEntity.ok(productServiceMapper.toProductDto(
        productService.saveProduct(productReference, productServiceMapper.toProductDetails(productDto))));
  }

  @DeleteMapping("{productReference}")
  public ResponseEntity<Void> deleteProduct(@PathVariable UUID productReference) {
    productService.deleteProductByReference(productReference);
    return ResponseEntity.noContent().build();
  }
}
