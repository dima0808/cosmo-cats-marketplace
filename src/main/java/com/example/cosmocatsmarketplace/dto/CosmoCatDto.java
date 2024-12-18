package com.example.cosmocatsmarketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CosmoCatDto {

  private UUID catReference;

  @NotBlank(message = "Name is mandatory")
  @Size(max = 50, message = "Name cannot exceed 50 characters")
  private String name;

  @NotBlank
  @Email(message = "Email should be valid")
  private String email;

  private String address;
  private String phoneNumber;
  private List<UUID> orders;
}
