package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.CosmoCatDetails;
import com.example.cosmocatsmarketplace.repository.CosmoCatRepository;
import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import com.example.cosmocatsmarketplace.repository.mapper.GeneralRepositoryMapper;
import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContacts;
import com.example.cosmocatsmarketplace.service.CosmoCatService;

import com.example.cosmocatsmarketplace.service.exception.CosmoCatNotFoundException;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CosmoCatServiceImpl implements CosmoCatService {

  private final CosmoCatRepository cosmoCatRepository;
  private final GeneralRepositoryMapper cosmoCatMapper;

  @Override
  public List<CosmoCatDetails> getAllCosmoCats() {
    return cosmoCatMapper.toCosmoCatDetails(cosmoCatRepository.findAll());
  }

  @Override
  public CosmoCatDetails getCosmoCatByReference(UUID catReference, boolean includeOrders) {
    CosmoCatEntity cosmoCatEntity = cosmoCatRepository.findByNaturalId(catReference)
        .orElseThrow(() -> new CosmoCatNotFoundException(catReference));
    if (includeOrders) {
      Hibernate.initialize(cosmoCatEntity.getOrders());
    }
    return cosmoCatMapper.toCosmoCatDetails(cosmoCatEntity);
  }

  @Override
  public CosmoCatDetails getCosmoCatByReference(UUID catReference) {
    return getCosmoCatByReference(catReference, false);
  }

  @Override
  public CosmoCatDetails saveCosmoCat(CosmoCatDetails cosmoCatDetails) {
    return cosmoCatMapper.toCosmoCatDetails(
        cosmoCatRepository.save(cosmoCatMapper.toCosmoCatEntity(cosmoCatDetails)));
  }

  @Override
  public CosmoCatDetails saveCosmoCat(UUID catReference, CosmoCatDetails cosmoCatDetails) {
    CosmoCatEntity existingCosmoCatEntity = cosmoCatRepository.findByNaturalId(catReference)
        .orElseThrow(() -> new CosmoCatNotFoundException(catReference));
    existingCosmoCatEntity.setName(cosmoCatDetails.getName());
    existingCosmoCatEntity.setEmail(cosmoCatDetails.getEmail());
    existingCosmoCatEntity.setAddress(cosmoCatDetails.getAddress());
    existingCosmoCatEntity.setPhoneNumber(cosmoCatDetails.getPhoneNumber());
    return cosmoCatMapper.toCosmoCatDetails(cosmoCatRepository.save(existingCosmoCatEntity));
  }

  @Override
  public void deleteCosmoCatByReference(UUID catReference) {
    getCosmoCatByReference(catReference);
    cosmoCatRepository.deleteByNaturalId(catReference);
  }

  @Override
  public List<CosmoCatContacts> getAllCosmoCatContacts() {
    return cosmoCatRepository.findAllByOrderByNameAsc();
  }

  @Override
  public CosmoCatContacts getCosmoCatContactsByEmail(String email) {
    return cosmoCatRepository.findByEmail(email)
        .orElseThrow(() -> new CosmoCatNotFoundException(email));
  }
}
