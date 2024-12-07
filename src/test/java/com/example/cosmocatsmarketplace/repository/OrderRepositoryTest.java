package com.example.cosmocatsmarketplace.repository;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderRepositoryTest {

  @Mock
  private OrderRepository orderRepository;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindOrderNumbersByCatReference() {
    UUID catReference = UUID.randomUUID();
    UUID orderNumber1 = UUID.randomUUID();
    UUID orderNumber2 = UUID.randomUUID();

    when(orderRepository.findOrderNumbersByCatReference(catReference)).thenReturn(List.of(orderNumber1, orderNumber2));

    List<UUID> result = orderRepository.findOrderNumbersByCatReference(catReference);
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(orderNumber1, orderNumber2);
  }
}