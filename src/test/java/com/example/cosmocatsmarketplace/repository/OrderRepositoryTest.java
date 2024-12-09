package com.example.cosmocatsmarketplace.repository;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryTest {

  @Mock
  private OrderRepository orderRepository;

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