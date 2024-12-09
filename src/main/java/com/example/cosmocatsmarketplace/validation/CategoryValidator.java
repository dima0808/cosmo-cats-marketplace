package com.example.cosmocatsmarketplace.validation;

import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.service.mapper.ProductServiceMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryValidator implements ConstraintValidator<ValidCategory, List<String>> {

  private final ProductServiceMapper productServiceMapper;

  @Override
  public boolean isValid(List<String> categories, ConstraintValidatorContext context) {
    return categories.stream()
        .map(String::toLowerCase)
        .allMatch(productServiceMapper.toCategoriesString(List.of(CategoryType.values())).stream()
            .map(String::toLowerCase)
            .toList()::contains);
  }
}
