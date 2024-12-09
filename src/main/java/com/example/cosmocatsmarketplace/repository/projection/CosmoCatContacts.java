package com.example.cosmocatsmarketplace.repository.projection;

import lombok.Builder;

@Builder
public record CosmoCatContacts(String name, String email, String phoneNumber) {
}
