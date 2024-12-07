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
public class CosmoCatDetails {

  private Long id;
  private UUID catReference;
  private String name;
  private String email;
  private String address;
  private String phoneNumber;
  private List<OrderDetails> orders;
}
