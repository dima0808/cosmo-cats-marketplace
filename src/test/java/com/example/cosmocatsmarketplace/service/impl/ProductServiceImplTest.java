package com.example.cosmocatsmarketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.domain.ProductDetails;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.repository.mapper.ProductRepositoryMapper;
import com.example.cosmocatsmarketplace.service.exception.ProductNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {ProductServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @MockBean
  private ProductRepository productRepository;

  @MockBean
  private ProductRepositoryMapper productRepositoryMapper;

  @Autowired
  private ProductServiceImpl productService;

  @Test
  void testGetAllProducts() {
    List<ProductEntity> entities = List.of(new ProductEntity());
    List<ProductDetails> details = List.of(new ProductDetails());

    when(productRepository.findAll()).thenReturn(entities);
    when(productRepositoryMapper.toProductDetails(entities)).thenReturn(details);

    List<ProductDetails> result = productService.getAllProducts();
    assertEquals(details, result);
  }

  @Test
  void testGetProductByReference() {
    UUID productReference = UUID.randomUUID();
    ProductEntity entity = new ProductEntity();
    ProductDetails details = new ProductDetails();

    when(productRepository.findByNaturalId(productReference)).thenReturn(Optional.of(entity));
    when(productRepositoryMapper.toProductDetails(entity)).thenReturn(details);

    ProductDetails result = productService.getProductByReference(productReference);
    assertEquals(details, result);
  }

  @Test
  void testGetProductByReference_NotFound() {
    UUID productReference = UUID.randomUUID();

    when(productRepository.findByNaturalId(productReference)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class,
        () -> productService.getProductByReference(productReference));
  }

  @Test
  void testSaveProduct() {
    ProductDetails productDetails = new ProductDetails();
    ProductEntity productEntity = new ProductEntity();
    ProductEntity savedEntity = new ProductEntity();
    ProductDetails savedDetails = new ProductDetails();

    when(productRepositoryMapper.toProductEntity(productDetails)).thenReturn(productEntity);
    when(productRepository.save(productEntity)).thenReturn(savedEntity);
    when(productRepositoryMapper.toProductDetails(savedEntity)).thenReturn(savedDetails);

    ProductDetails result = productService.saveProduct(productDetails);
    assertEquals(savedDetails, result);
  }

  @Test
  void testSaveProductWithId() {
    UUID productReference = UUID.randomUUID();
    ProductDetails productDetails = new ProductDetails();
    productDetails.setCategories(List.of(CategoryType.ELECTRONICS));
    ProductEntity existingEntity = new ProductEntity();
    ProductEntity savedEntity = new ProductEntity();
    ProductDetails savedDetails = new ProductDetails();

    when(productRepository.findByNaturalId(productReference)).thenReturn(Optional.of(existingEntity));
    when(productRepository.save(existingEntity)).thenReturn(savedEntity);
    when(productRepositoryMapper.toProductDetails(savedEntity)).thenReturn(savedDetails);

    ProductDetails result = productService.saveProduct(productReference, productDetails);
    assertEquals(savedDetails, result);
  }

  @Test
  void testSaveProductWithId_NotFound() {
    UUID productReference = UUID.randomUUID();
    ProductDetails productDetails = new ProductDetails();

    when(productRepository.findByNaturalId(productReference)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class,
        () -> productService.saveProduct(productReference, productDetails));
  }

  @Test
  void testDeleteProductByReference() {
    UUID productReference = UUID.randomUUID();
    ProductEntity entity = new ProductEntity();

    when(productRepository.findByNaturalId(productReference)).thenReturn(Optional.of(entity));
    doNothing().when(productRepository).deleteByNaturalId(productReference);

    productService.deleteProductByReference(productReference);

    verify(productRepository, times(1)).deleteByNaturalId(productReference);
  }

  @Test
  void testDeleteProductByReference_NotFound() {
    UUID productReference = UUID.randomUUID();

    when(productRepository.findByNaturalId(productReference)).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class,
        () -> productService.deleteProductByReference(productReference));
  }
}
