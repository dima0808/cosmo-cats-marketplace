package com.example.cosmocatsmarketplace.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CosmoCatListDto {

  private List<CosmoCatDto> cosmoCats;
}
