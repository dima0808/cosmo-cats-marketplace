package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.AbstractIt;
import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.aspect.FeatureToggleAspect;
import com.example.cosmocatsmarketplace.featureToggle.service.FeatureToggleService;
import com.example.cosmocatsmarketplace.repository.CosmoCatRepository;
import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import com.example.cosmocatsmarketplace.service.CosmoCatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CosmoCat Controller IT")
class CosmoCatControllerIT extends AbstractIt {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CosmoCatRepository cosmoCatRepository;

  @SpyBean
  private CosmoCatService cosmoCatService;

  @MockBean
  private FeatureToggleService featureToggleService;

  @Autowired
  private ObjectMapper objectMapper;

  @TestConfiguration
  @EnableAspectJAutoProxy
  static class TestConfig {
    @Bean
    public FeatureToggleAspect featureToggleAspect() {
      return new FeatureToggleAspect();
    }
  }

  @BeforeEach
  void setUp() {
    reset(cosmoCatService);
    cosmoCatRepository.deleteAll();
  }

  @Test
  @SneakyThrows
  void testGetCosmoCatsWhenFeatureToggleIsEnabled() {
    when(featureToggleService.check(FeatureToggles.COSMO_CATS.getFeatureName())).thenReturn(true);
    saveCosmoCatEntity();

    mockMvc.perform(get("/api/v1/cosmo-cats")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testGetCosmoCatsWhenFeatureToggleIsDisabled() {
    when(featureToggleService.check(FeatureToggles.COSMO_CATS.getFeatureName())).thenReturn(false);
    saveCosmoCatEntity();

    mockMvc.perform(get("/api/v1/cosmo-cats")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @SneakyThrows
  void testGetAllCosmoCatsContacts() {
    saveCosmoCatEntity();

    mockMvc.perform(get("/api/v1/cosmo-cats/contacts")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testGetCosmoCatByReference() {
    CosmoCatEntity cosmoCatEntity = saveCosmoCatEntity();

    mockMvc.perform(get("/api/v1/cosmo-cats/{catReference}", cosmoCatEntity.getCatReference())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testCreateCosmoCat() {
    CosmoCatDto cosmoCatDto = new CosmoCatDto();
    cosmoCatDto.setName("Stellar");
    cosmoCatDto.setEmail("stellar@cosmo.cats");
    cosmoCatDto.setAddress("Orion, Nebula Zone, K-9 Cluster");
    cosmoCatDto.setPhoneNumber("333-666-999");

    mockMvc.perform(post("/api/v1/cosmo-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cosmoCatDto)))
        .andExpect(status().isCreated());
  }

  @Test
  @SneakyThrows
  void testUpdateCosmoCat() {
    CosmoCatEntity cosmoCatEntity = saveCosmoCatEntity();
    CosmoCatDto cosmoCatDto = new CosmoCatDto();
    cosmoCatDto.setName("Updated Name");
    cosmoCatDto.setEmail("updated@cosmo.cats");
    cosmoCatDto.setAddress("Updated Address");
    cosmoCatDto.setPhoneNumber("987-654-3210");

    mockMvc.perform(put("/api/v1/cosmo-cats/{catReference}", cosmoCatEntity.getCatReference())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cosmoCatDto)))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testDeleteCosmoCat() {
    CosmoCatEntity cosmoCatEntity = saveCosmoCatEntity();

    mockMvc.perform(delete("/api/v1/cosmo-cats/{catReference}", cosmoCatEntity.getCatReference())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  private CosmoCatEntity saveCosmoCatEntity() {
    return cosmoCatRepository.save(CosmoCatEntity.builder()
        .catReference(UUID.randomUUID())
        .name("Zebulon")
        .email("zebulon@cosmo.cats")
        .address("Andromeda, Sector 42, Alpha Centauri")
        .phoneNumber("123-456-7890")
        .build());
  }
}
