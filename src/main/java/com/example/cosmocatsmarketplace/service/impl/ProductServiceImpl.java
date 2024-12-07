package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.ProductDetails;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.repository.mapper.GeneralRepositoryMapper;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.exception.ProductNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final GeneralRepositoryMapper productMapper;

  @Override
  @Transactional(readOnly = true)
  public List<ProductDetails> getAllProducts() {
    return productMapper.toProductDetails(productRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public ProductDetails getProductById(Long productId) {
    return productMapper.toProductDetails(
        productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId)));
  }

  @Override
  @Transactional(propagation = Propagation.NESTED)
  public ProductDetails saveProduct(ProductDetails productDetails) {
    return productMapper.toProductDetails(
        productRepository.save(productMapper.toProductEntity(productDetails)));
  }

  @Override
  @Transactional(propagation = Propagation.NESTED)
  public ProductDetails saveProduct(Long productId, ProductDetails productDetails) {
    ProductEntity existingProduct = productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
    existingProduct.setName(productDetails.getName());
    existingProduct.setDescription(productDetails.getDescription());
    existingProduct.setPrice(productDetails.getPrice());
    existingProduct.setCategories(new ArrayList<>(productDetails.getCategories()));
    return productMapper.toProductDetails(productRepository.save(existingProduct));
  }

  @Override
  @Transactional
  public void deleteProductById(Long productId) {
    getProductById(productId);
    productRepository.deleteById(productId);
  }
}
