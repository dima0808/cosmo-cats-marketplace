package com.example.cosmocatsmarketplace.repository;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductRepositoryTest {

  @Mock
  private ProductRepository productRepository;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindById() {
    Long productId = 1L;
    ProductEntity product = new ProductEntity();
    product.setId(productId);
    product.setName("Test Product");

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    Optional<ProductEntity> result = productRepository.findById(productId);
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(productId);
    assertThat(result.get().getName()).isEqualTo("Test Product");
  }

  @Test
  void testSave() {
    ProductEntity product = new ProductEntity();
    product.setName("New Product");

    when(productRepository.save(product)).thenReturn(product);

    ProductEntity result = productRepository.save(product);
    assertThat(result.getName()).isEqualTo("New Product");
  }

  @Test
  void testDeleteById() {
    Long productId = 1L;
    productRepository.deleteById(productId);
  }
}