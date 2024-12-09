package com.example.cosmocatsmarketplace.common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Deprecated
public class CustomErrorResponse {

  private int status;
  private String error;
  private String message;
  private String path;
}
