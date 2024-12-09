package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.ProductDetails;
import java.util.List;
import java.util.UUID;

public interface ProductService {

  List<ProductDetails> getAllProducts();

  ProductDetails getProductByReference(UUID productReference);

  ProductDetails saveProduct(ProductDetails productDetails);

  ProductDetails saveProduct(UUID productReference, ProductDetails productDetails);

  void deleteProductByReference(UUID productReference);
}
