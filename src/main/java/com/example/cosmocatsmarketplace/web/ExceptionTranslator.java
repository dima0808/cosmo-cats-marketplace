package com.example.cosmocatsmarketplace.web;

import static com.example.cosmocatsmarketplace.util.ValidationUtils.getErrorResponseOfFieldErrors;
import static java.net.URI.create;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

import com.example.cosmocatsmarketplace.featureToggle.exception.FeatureToggleNotEnabledException;
import com.example.cosmocatsmarketplace.service.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ProblemDetail> handleProductNotFoundException(ProductNotFoundException ex) {
    log.info("Product Not Found exception raised");
    ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
    problemDetail.setType(create("product-not-found"));
    problemDetail.setTitle("Product Not Found");
    return ResponseEntity.status(NOT_FOUND).body(problemDetail);
  }

  @ExceptionHandler(FeatureToggleNotEnabledException.class)
  public ResponseEntity<String> handleFeatureToggleNotEnabled(FeatureToggleNotEnabledException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
    log.info("Input params validation failed");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(getErrorResponseOfFieldErrors(ex.getBindingResult().getFieldErrors()));
  }
}
