package com.example.cosmocatsmarketplace.domain;

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
public class OrderDetails {

  private Long id;
  UUID orderNumber;
  private List<OrderEntryDetails> orderEntries;
}
