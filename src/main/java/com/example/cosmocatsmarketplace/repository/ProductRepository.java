package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID> {
}
