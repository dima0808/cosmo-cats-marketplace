package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContacts;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface CosmoCatRepository extends NaturalIdRepository<CosmoCatEntity, UUID> {

  Optional<CosmoCatContacts> findByEmail(String email);

  List<CosmoCatContacts> findAllByOrderByNameAsc();
}
