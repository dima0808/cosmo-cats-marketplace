package com.example.cosmocatsmarketplace.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DataWrapperDto {

  private Object data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Object included;
}
