package com.example.cosmocatsmarketplace.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDto {

  UUID orderNumber;
  List<OrderEntryDto> orderEntries;
}
