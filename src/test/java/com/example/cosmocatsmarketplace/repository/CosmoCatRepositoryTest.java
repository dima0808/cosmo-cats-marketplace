package com.example.cosmocatsmarketplace.repository;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContacts;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CosmoCatRepositoryTest {

  @Mock
  private CosmoCatRepository cosmoCatRepository;

  @Test
  void testFindByEmail() {
    String email = "test@example.com";
    CosmoCatContacts contact = CosmoCatContacts.builder()
        .name("Test Name")
        .email("test@example.com")
        .phoneNumber("+380999999999")
        .build();

    when(cosmoCatRepository.findByEmail(email)).thenReturn(Optional.of(contact));

    Optional<CosmoCatContacts> result = cosmoCatRepository.findByEmail(email);
    assertThat(result).isPresent();
    assertThat(result.get().email()).isEqualTo(email);
  }

  @Test
  void testFindAllByOrderByNameAsc() {
    CosmoCatContacts contact1 = CosmoCatContacts.builder()
        .name("Alice")
        .email("test1@example.com")
        .phoneNumber("+380999999999")
        .build();

    CosmoCatContacts contact2 = CosmoCatContacts.builder()
        .name("Bob")
        .email("test2@example.com")
        .phoneNumber("+380999999999")
        .build();

    when(cosmoCatRepository.findAllByOrderByNameAsc()).thenReturn(List.of(contact1, contact2));

    List<CosmoCatContacts> result = cosmoCatRepository.findAllByOrderByNameAsc();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).name()).isEqualTo("Alice");
    assertThat(result.get(1).name()).isEqualTo("Bob");
  }
}