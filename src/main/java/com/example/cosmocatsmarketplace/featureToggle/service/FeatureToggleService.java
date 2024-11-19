package com.example.cosmocatsmarketplace.featureToggle.service;

import com.example.cosmocatsmarketplace.featureToggle.config.FeatureToggleProperties;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FeatureToggleService {

  private final ConcurrentHashMap<String, Boolean> featureToggles;

  public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
    featureToggles = new ConcurrentHashMap<>(featureToggleProperties.getToggles());
  }

  public boolean check(String featureName) {
    return featureToggles.getOrDefault(featureName, false);
  }
}
