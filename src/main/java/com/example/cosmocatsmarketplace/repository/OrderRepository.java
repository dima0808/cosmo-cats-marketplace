package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends NaturalIdRepository<OrderEntity, UUID> {

  @Query("SELECT o.orderNumber FROM OrderEntity o WHERE o.cosmoCat.catReference = :catReference")
  List<UUID> findOrderNumbersByCatReference(@Param("catReference") UUID catReference);
}
