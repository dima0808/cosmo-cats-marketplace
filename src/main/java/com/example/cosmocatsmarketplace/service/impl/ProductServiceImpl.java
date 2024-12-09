package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.ProductDetails;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.repository.mapper.ProductRepositoryMapper;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.exception.ProductNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductRepositoryMapper productRepositoryMapper;

  @Override
  @Transactional(readOnly = true)
  public List<ProductDetails> getAllProducts() {
    return productRepositoryMapper.toProductDetails(productRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public ProductDetails getProductByReference(UUID productReference) {
    return productRepositoryMapper.toProductDetails(
        productRepository.findByNaturalId(productReference)
            .orElseThrow(() -> new ProductNotFoundException(productReference)));
  }

  @Override
  @Transactional(propagation = Propagation.NESTED)
  public ProductDetails saveProduct(ProductDetails productDetails) {
    return productRepositoryMapper.toProductDetails(
        productRepository.save(productRepositoryMapper.toProductEntity(productDetails)));
  }

  @Override
  @Transactional(propagation = Propagation.NESTED)
  public ProductDetails saveProduct(UUID productReference, ProductDetails productDetails) {
    ProductEntity existingProduct = productRepository.findByNaturalId(productReference)
        .orElseThrow(() -> new ProductNotFoundException(productReference));
    existingProduct.setName(productDetails.getName());
    existingProduct.setDescription(productDetails.getDescription());
    existingProduct.setPrice(productDetails.getPrice());
    existingProduct.setCategories(new ArrayList<>(productDetails.getCategories()));
    return productRepositoryMapper.toProductDetails(productRepository.save(existingProduct));
  }

  @Override
  @Transactional
  public void deleteProductByReference(UUID productReference) {
    getProductByReference(productReference);
    productRepository.deleteByNaturalId(productReference);
  }
}
