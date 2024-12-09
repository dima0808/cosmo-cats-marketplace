package com.example.cosmocatsmarketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.cosmocatsmarketplace.domain.CosmoCatDetails;
import com.example.cosmocatsmarketplace.repository.CosmoCatRepository;
import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import com.example.cosmocatsmarketplace.repository.mapper.CosmoCatRepositoryMapper;
import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContacts;
import com.example.cosmocatsmarketplace.service.exception.CosmoCatNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {CosmoCatServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class CosmoCatServiceImplTest {

  @MockBean
  private CosmoCatRepository cosmoCatRepository;

  @MockBean
  private CosmoCatRepositoryMapper cosmoCatRepositoryMapper;

  @Autowired
  private CosmoCatServiceImpl cosmoCatService;

  @Test
  void testGetAllCosmoCats() {
    List<CosmoCatEntity> entities = List.of(new CosmoCatEntity());
    List<CosmoCatDetails> details = List.of(new CosmoCatDetails());

    when(cosmoCatRepository.findAll()).thenReturn(entities);
    when(cosmoCatRepositoryMapper.toCosmoCatDetails(entities)).thenReturn(details);

    List<CosmoCatDetails> result = cosmoCatService.getAllCosmoCats();
    assertEquals(details, result);
  }

  @Test
  void testGetCosmoCatByReference() {
    UUID catReference = UUID.randomUUID();
    CosmoCatEntity entity = new CosmoCatEntity();
    CosmoCatDetails details = new CosmoCatDetails();

    when(cosmoCatRepository.findByNaturalId(catReference)).thenReturn(Optional.of(entity));
    when(cosmoCatRepositoryMapper.toCosmoCatDetails(entity)).thenReturn(details);

    CosmoCatDetails result = cosmoCatService.getCosmoCatByReference(catReference);
    assertEquals(details, result);
  }

  @Test
  void testGetCosmoCatByReference_NotFound() {
    UUID catReference = UUID.randomUUID();

    when(cosmoCatRepository.findByNaturalId(catReference)).thenReturn(Optional.empty());

    assertThrows(CosmoCatNotFoundException.class, () -> cosmoCatService.getCosmoCatByReference(catReference));
  }

  @Test
  void testSaveCosmoCat() {
    CosmoCatDetails details = new CosmoCatDetails();
    CosmoCatEntity entity = new CosmoCatEntity();

    when(cosmoCatRepositoryMapper.toCosmoCatEntity(details)).thenReturn(entity);
    when(cosmoCatRepository.save(entity)).thenReturn(entity);
    when(cosmoCatRepositoryMapper.toCosmoCatDetails(entity)).thenReturn(details);

    CosmoCatDetails result = cosmoCatService.saveCosmoCat(details);
    assertEquals(details, result);
  }

  @Test
  void testSaveCosmoCat_WithReference() {
    UUID catReference = UUID.randomUUID();
    CosmoCatDetails details = new CosmoCatDetails();
    CosmoCatEntity entity = new CosmoCatEntity();

    when(cosmoCatRepository.findByNaturalId(catReference)).thenReturn(Optional.of(entity));
    when(cosmoCatRepository.save(entity)).thenReturn(entity);
    when(cosmoCatRepositoryMapper.toCosmoCatDetails(entity)).thenReturn(details);

    CosmoCatDetails result = cosmoCatService.saveCosmoCat(catReference, details);
    assertEquals(details, result);
  }

  @Test
  void testSaveCosmoCat_WithReference_NotFound() {
    UUID catReference = UUID.randomUUID();
    CosmoCatDetails details = new CosmoCatDetails();

    when(cosmoCatRepository.findByNaturalId(catReference)).thenReturn(Optional.empty());

    assertThrows(CosmoCatNotFoundException.class, () -> cosmoCatService.saveCosmoCat(catReference, details));
  }

  @Test
  void testDeleteCosmoCatByReference() {
    UUID catReference = UUID.randomUUID();
    CosmoCatEntity entity = new CosmoCatEntity();

    when(cosmoCatRepository.findByNaturalId(catReference)).thenReturn(Optional.of(entity));

    cosmoCatService.deleteCosmoCatByReference(catReference);

    verify(cosmoCatRepository).deleteByNaturalId(catReference);
  }

  @Test
  void testDeleteCosmoCatByReference_NotFound() {
    UUID catReference = UUID.randomUUID();

    when(cosmoCatRepository.findByNaturalId(catReference)).thenReturn(Optional.empty());

    assertThrows(CosmoCatNotFoundException.class, () -> cosmoCatService.deleteCosmoCatByReference(catReference));
  }

  @Test
  void testGetAllCosmoCatContacts() {
    List<CosmoCatContacts> contacts = List.of(mock(CosmoCatContacts.class));

    when(cosmoCatRepository.findAllByOrderByNameAsc()).thenReturn(contacts);

    List<CosmoCatContacts> result = cosmoCatService.getAllCosmoCatContacts();
    assertEquals(contacts, result);
  }

  @Test
  void testGetCosmoCatContactsByEmail() {
    String email = "test@example.com";
    CosmoCatContacts contacts = mock(CosmoCatContacts.class);

    when(cosmoCatRepository.findByEmail(email)).thenReturn(Optional.of(contacts));

    CosmoCatContacts result = cosmoCatService.getCosmoCatContactsByEmail(email);
    assertEquals(contacts, result);
  }

  @Test
  void testGetCosmoCatContactsByEmail_NotFound() {
    String email = "test@example.com";

    when(cosmoCatRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThrows(CosmoCatNotFoundException.class, () -> cosmoCatService.getCosmoCatContactsByEmail(email));
  }
}
