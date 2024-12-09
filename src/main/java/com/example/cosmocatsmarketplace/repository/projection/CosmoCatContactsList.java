package com.example.cosmocatsmarketplace.repository.projection;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CosmoCatContactsList {

  private List<CosmoCatContacts> contacts;
}
