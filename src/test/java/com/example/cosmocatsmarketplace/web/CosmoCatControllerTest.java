package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.dto.CosmoCatDto;
import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.aspect.FeatureToggleAspect;
import com.example.cosmocatsmarketplace.featureToggle.service.FeatureToggleService;
import com.example.cosmocatsmarketplace.service.CosmoCatService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CosmoCatController.class)
class CosmoCatControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CosmoCatService cosmoCatService;

  @MockBean
  private FeatureToggleService featureToggleService;

  @TestConfiguration
  @EnableAspectJAutoProxy
  static class TestConfig {
    @Bean
    public FeatureToggleAspect featureToggleAspect() {
      return new FeatureToggleAspect();
    }
  }

  @Test
  void testGetCosmoCatsWhenFeatureToggleIsEnabled() throws Exception {
    when(featureToggleService.check(FeatureToggles.COSMO_CATS.getFeatureName())).thenReturn(true);
    List<CosmoCatDto> cosmoCats = Collections.singletonList(CosmoCatDto.builder().build());
    when(cosmoCatService.getCosmoCats()).thenReturn(cosmoCats);

    mockMvc.perform(get("/api/v1/cosmo-cats")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists());
  }

  @Test
  void testGetCosmoCatsWhenFeatureToggleIsDisabled() throws Exception {
    when(featureToggleService.check(FeatureToggles.COSMO_CATS.getFeatureName())).thenReturn(false);

    System.out.println(featureToggleService.check(FeatureToggles.COSMO_CATS.getFeatureName()));

    mockMvc.perform(get("/api/v1/cosmo-cats")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
