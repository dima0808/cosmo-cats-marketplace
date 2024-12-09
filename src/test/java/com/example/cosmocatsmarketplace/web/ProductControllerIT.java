package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.AbstractIt;
import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.dto.ProductDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.aspect.FeatureToggleAspect;
import com.example.cosmocatsmarketplace.featureToggle.service.FeatureToggleService;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
class ProductControllerIT extends AbstractIt {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProductRepository productRepository;

  @SpyBean
  private ProductService productService;

  @MockBean
  private FeatureToggleService featureToggleService;

  @Autowired
  private ObjectMapper objectMapper;


  @TestConfiguration
  @EnableAspectJAutoProxy
  static class TestConfig {
    @Bean
    public FeatureToggleAspect featureToggleAspect() {
      return new FeatureToggleAspect();
    }
  }

  @BeforeEach
  void setUp() {
    reset(productService);
  }

  @Test
  @SneakyThrows
  void testGetAllProductsWhenFeatureToggleIsEnabled() {
    when(featureToggleService.check(FeatureToggles.KITTY_PRODUCTS.getFeatureName())).thenReturn(true);
    saveProductEntity();

    mockMvc.perform(get("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testGetAllProductsWhenFeatureToggleIsDisabled() {
    when(featureToggleService.check(FeatureToggles.KITTY_PRODUCTS.getFeatureName())).thenReturn(false);
    saveProductEntity();

    mockMvc.perform(get("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @SneakyThrows
  void testGetProductById() {
    when(featureToggleService.check(FeatureToggles.KITTY_PRODUCTS.getFeatureName())).thenReturn(true);
    ProductEntity productEntity = saveProductEntity();

    mockMvc.perform(get("/api/v1/products/{productReference}", productEntity.getProductReference())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testCreateProduct() {
    when(featureToggleService.check(FeatureToggles.KITTY_PRODUCTS.getFeatureName())).thenReturn(true);

    ProductDto productDto = new ProductDto();
    productDto.setName("Test Product");
    productDto.setDescription("Test Description");
    productDto.setPrice(100);
    productDto.setCategories(List.of("Electronics"));

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productDto)))
        .andExpect(status().isCreated());
  }

  @Test
  @SneakyThrows
  void testUpdateProduct() {
    ProductEntity productEntity = saveProductEntity();

    when(featureToggleService.check(FeatureToggles.KITTY_PRODUCTS.getFeatureName())).thenReturn(true);
    ProductDto productDto = new ProductDto();
    productDto.setName("Updated Product");
    productDto.setDescription("Updated Description");
    productDto.setPrice(200);
    productDto.setCategories(List.of("Clothes", "Other"));

    mockMvc.perform(put("/api/v1/products/{productReference}", productEntity.getProductReference())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productDto)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testDeleteProduct() {
    ProductEntity productEntity = saveProductEntity();

    mockMvc.perform(delete("/api/v1/products/{productReference}", productEntity.getProductReference())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  private ProductEntity saveProductEntity() {
    return productRepository.save(ProductEntity.builder()
        .productReference(UUID.randomUUID())
        .name("Test Product")
        .description("Test Description")
        .price(100)
        .categories(List.of(CategoryType.ELECTRONICS))
        .build());
  }
}
