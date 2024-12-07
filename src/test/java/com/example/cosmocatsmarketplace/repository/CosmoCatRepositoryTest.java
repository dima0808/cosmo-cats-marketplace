package com.example.cosmocatsmarketplace.repository;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.cosmocatsmarketplace.repository.projection.CosmoCatContacts;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CosmoCatRepositoryTest {

  @Mock
  private CosmoCatRepository cosmoCatRepository;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindByEmail() {
    String email = "test@example.com";
    CosmoCatContacts contact = new CosmoCatContacts() {
      @Override
      public String getEmail() {
        return email;
      }

      @Override
      public String getPhoneNumber() {
        return "+380999999999";
      }

      @Override
      public String getName() {
        return "Test Name";
      }
    };

    when(cosmoCatRepository.findByEmail(email)).thenReturn(Optional.of(contact));

    Optional<CosmoCatContacts> result = cosmoCatRepository.findByEmail(email);
    assertThat(result).isPresent();
    assertThat(result.get().getEmail()).isEqualTo(email);
  }

  @Test
  void testFindAllByOrderByNameAsc() {
    CosmoCatContacts contact1 = new CosmoCatContacts() {
      @Override
      public String getEmail() {
        return "test1@example.com";
      }

      @Override
      public String getPhoneNumber() {
        return "+380999999999";
      }

      @Override
      public String getName() {
        return "Alice";
      }
    };

    CosmoCatContacts contact2 = new CosmoCatContacts() {
      @Override
      public String getEmail() {
        return "test2@example.com";
      }

      @Override
      public String getPhoneNumber() {
        return "+380999999999";
      }

      @Override
      public String getName() {
        return "Bob";
      }
    };

    when(cosmoCatRepository.findAllByOrderByNameAsc()).thenReturn(List.of(contact1, contact2));

    List<CosmoCatContacts> result = cosmoCatRepository.findAllByOrderByNameAsc();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getName()).isEqualTo("Alice");
    assertThat(result.get(1).getName()).isEqualTo("Bob");
  }
}