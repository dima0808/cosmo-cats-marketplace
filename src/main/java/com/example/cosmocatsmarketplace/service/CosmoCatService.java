package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.CosmoCatDetails;
import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContacts;
import java.util.List;
import java.util.UUID;

public interface CosmoCatService {

    List<CosmoCatDetails> getAllCosmoCats();

    CosmoCatDetails getCosmoCatByReference(UUID catReference, boolean includeOrders);

    CosmoCatDetails getCosmoCatByReference(UUID catReference);

    CosmoCatDetails saveCosmoCat(CosmoCatDetails cosmoCatDetails);

    CosmoCatDetails saveCosmoCat(UUID catReference, CosmoCatDetails cosmoCatDetails);

    void deleteCosmoCatByReference(UUID catReference);

    List<CosmoCatContacts> getAllCosmoCatContacts();

    CosmoCatContacts getCosmoCatContactsByEmail(String email);
}
