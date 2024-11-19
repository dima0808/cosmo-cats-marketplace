package com.example.cosmocatsmarketplace.featureToggle.aspect;

import com.example.cosmocatsmarketplace.featureToggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featureToggle.annotation.FeatureToggle;
import com.example.cosmocatsmarketplace.featureToggle.exception.FeatureToggleNotEnabledException;
import com.example.cosmocatsmarketplace.featureToggle.service.FeatureToggleService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class FeatureToggleAspect {

  @Autowired
  private FeatureToggleService featureToggleService;

  @Before("@annotation(featureToggle)")
  public void checkFeatureToggleBefore(FeatureToggle featureToggle) {
    FeatureToggles toggle = featureToggle.value();
    if (!featureToggleService.check(toggle.getFeatureName())) {
      log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
      throw new FeatureToggleNotEnabledException(toggle.getFeatureName());
    }
  }
}
