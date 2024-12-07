package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.ProductDetails;
import java.util.List;

public interface ProductService {

  List<ProductDetails> getAllProducts();

  ProductDetails getProductById(Long productId);

  ProductDetails saveProduct(ProductDetails productDetails);

  ProductDetails saveProduct(Long productId, ProductDetails productDetails);

  void deleteProductById(Long productId);
}
