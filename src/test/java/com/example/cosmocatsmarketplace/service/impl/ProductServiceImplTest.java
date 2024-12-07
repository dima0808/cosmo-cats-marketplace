package com.example.cosmocatsmarketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.domain.ProductDetails;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.repository.mapper.GeneralRepositoryMapper;
import com.example.cosmocatsmarketplace.service.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private GeneralRepositoryMapper productMapper;

  @InjectMocks
  private ProductServiceImpl productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllProducts() {
    List<ProductEntity> entities = List.of(new ProductEntity());
    List<ProductDetails> details = List.of(new ProductDetails());

    when(productRepository.findAll()).thenReturn(entities);
    when(productMapper.toProductDetails(entities)).thenReturn(details);

    List<ProductDetails> result = productService.getAllProducts();
    assertEquals(details, result);
  }

  @Test
  void testGetProductById() {
    Long productId = 1L;
    ProductEntity entity = new ProductEntity();
    ProductDetails details = new ProductDetails();

    when(productRepository.findById(productId)).thenReturn(Optional.of(entity));
    when(productMapper.toProductDetails(entity)).thenReturn(details);

    ProductDetails result = productService.getProductById(productId);
    assertEquals(details, result);
  }

  @Test
  void testGetProductById_NotFound() {
    Long productId = 1L;

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
  }

  @Test
  void testSaveProduct() {
    ProductDetails productDetails = new ProductDetails();
    ProductEntity productEntity = new ProductEntity();
    ProductEntity savedEntity = new ProductEntity();
    ProductDetails savedDetails = new ProductDetails();

    when(productMapper.toProductEntity(productDetails)).thenReturn(productEntity);
    when(productRepository.save(productEntity)).thenReturn(savedEntity);
    when(productMapper.toProductDetails(savedEntity)).thenReturn(savedDetails);

    ProductDetails result = productService.saveProduct(productDetails);
    assertEquals(savedDetails, result);
  }

  @Test
  void testSaveProductWithId() {
    Long productId = 1L;
    ProductDetails productDetails = new ProductDetails();
    productDetails.setCategories(List.of(CategoryType.ELECTRONICS));
    ProductEntity existingEntity = new ProductEntity();
    ProductEntity savedEntity = new ProductEntity();
    ProductDetails savedDetails = new ProductDetails();

    when(productRepository.findById(productId)).thenReturn(Optional.of(existingEntity));
    when(productRepository.save(existingEntity)).thenReturn(savedEntity);
    when(productMapper.toProductDetails(savedEntity)).thenReturn(savedDetails);

    ProductDetails result = productService.saveProduct(productId, productDetails);
    assertEquals(savedDetails, result);
  }

  @Test
  void testSaveProductWithId_NotFound() {
    Long productId = 1L;
    ProductDetails productDetails = new ProductDetails();

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> productService.saveProduct(productId, productDetails));
  }

  @Test
  void testDeleteProductById() {
    Long productId = 1L;
    ProductEntity entity = new ProductEntity();

    when(productRepository.findById(productId)).thenReturn(Optional.of(entity));
    doNothing().when(productRepository).deleteById(productId);

    productService.deleteProductById(productId);

    verify(productRepository, times(1)).deleteById(productId);
  }

  @Test
  void testDeleteProductById_NotFound() {
    Long productId = 1L;

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(productId));
  }
}
