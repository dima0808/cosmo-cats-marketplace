package com.example.cosmocatsmarketplace.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.cosmocatsmarketplace.domain.OrderDetails;
import com.example.cosmocatsmarketplace.repository.CosmoCatRepository;
import com.example.cosmocatsmarketplace.repository.OrderRepository;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.repository.mapper.GeneralRepositoryMapper;
import com.example.cosmocatsmarketplace.service.exception.OrderNotFoundException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class OrderServiceImplTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private CosmoCatRepository cosmoCatRepository;

  @Mock
  private GeneralRepositoryMapper orderMapper;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private OrderServiceImpl orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllOrders() {
    List<OrderEntity> entities = List.of(new OrderEntity());
    List<OrderDetails> details = List.of(new OrderDetails());

    when(orderRepository.findAll()).thenReturn(entities);
    when(orderMapper.toOrderDetails(entities)).thenReturn(details);

    List<OrderDetails> result = orderService.getAllOrders();
    assertEquals(details, result);
  }

  @Test
  void testGetOrderByNumber() {
    UUID orderNumber = UUID.randomUUID();
    OrderEntity entity = new OrderEntity();
    OrderDetails details = new OrderDetails();

    when(orderRepository.findByNaturalId(orderNumber)).thenReturn(Optional.of(entity));
    when(orderMapper.toOrderDetails(entity)).thenReturn(details);

    OrderDetails result = orderService.getOrderByNumber(orderNumber);
    assertEquals(details, result);
  }

  @Test
  void testGetOrderByNumber_NotFound() {
    UUID orderNumber = UUID.randomUUID();

    when(orderRepository.findByNaturalId(orderNumber)).thenReturn(Optional.empty());

    assertThrows(OrderNotFoundException.class, () -> orderService.getOrderByNumber(orderNumber));
  }

  @Test
  void testSaveOrder() {
    UUID catReference = UUID.randomUUID();
    UUID orderNumber = UUID.randomUUID();
    OrderDetails orderDetails = new OrderDetails();
    orderDetails.setOrderNumber(orderNumber);
    orderDetails.setOrderEntries(new ArrayList<>());

    CosmoCatEntity cosmoCatEntity = new CosmoCatEntity();
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setOrderNumber(orderNumber);
    orderEntity.setCosmoCat(cosmoCatEntity);

    when(cosmoCatRepository.findByNaturalId(catReference)).thenReturn(Optional.of(cosmoCatEntity));
    when(productRepository.findById(anyLong())).thenReturn(Optional.of(new ProductEntity()));
    when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
    when(orderMapper.toOrderDetails(any(OrderEntity.class))).thenReturn(orderDetails);

    OrderDetails result = orderService.saveOrder(catReference, orderDetails);
    assertEquals(orderDetails, result);
  }

  @Test
  void testDeleteOrderByNumber() {
    UUID orderNumber = UUID.randomUUID();
    OrderDetails orderDetails = new OrderDetails();
    orderDetails.setOrderNumber(orderNumber);

    when(orderRepository.findByNaturalId(orderNumber)).thenReturn(Optional.of(new OrderEntity()));
    doNothing().when(orderRepository).deleteByNaturalId(orderNumber);

    orderService.deleteOrderByNumber(orderNumber);
    verify(orderRepository, times(1)).deleteByNaturalId(orderNumber);
  }

  @Test
  void testGetOrderNumbersByCatReference() {
    UUID catReference = UUID.randomUUID();
    List<UUID> orderNumbers = List.of(UUID.randomUUID(), UUID.randomUUID());

    when(orderRepository.findOrderNumbersByCatReference(catReference)).thenReturn(orderNumbers);

    List<UUID> result = orderService.getOrderNumbersByCatReference(catReference);
    assertEquals(orderNumbers, result);
  }
}
