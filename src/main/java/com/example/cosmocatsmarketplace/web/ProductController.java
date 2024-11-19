package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.ProductDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.annotation.FeatureToggle;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.mapper.ProductMapper;
import jakarta.validation.Valid;
import java.util.List;
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
  private final ProductMapper productMapper;

  @GetMapping
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts().stream()
        .map(productMapper::toProductDto)
        .toList());
  }

  @GetMapping("{productId}")
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
    return ResponseEntity.ok(productMapper.toProductDto(productService.getProductById(productId)));
  }

  @PostMapping
  @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
  public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toProductDto(
        productService.createProduct(productMapper.toProduct(productDto))));
  }

  @PutMapping("{productId}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,
      @RequestBody ProductDto productDto) {
    Product product = productMapper.toProduct(productDto);
    product.setId(productId);
    return ResponseEntity.ok(productMapper.toProductDto(productService.updateProduct(product)));
  }

  @DeleteMapping("{productId}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
    productService.deleteProductById(productId);
    return ResponseEntity.noContent().build();
  }
}