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
import com.example.cosmocatsmarketplace.repository.mapper.OrderRepositoryMapper;
import com.example.cosmocatsmarketplace.service.exception.OrderNotFoundException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {OrderServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

  @MockBean
  private OrderRepository orderRepository;

  @MockBean
  private CosmoCatRepository cosmoCatRepository;

  @MockBean
  private OrderRepositoryMapper orderRepositoryMapper;

  @MockBean
  private ProductRepository productRepository;

  @Autowired
  private OrderServiceImpl orderService;

  @Test
  void testGetAllOrders() {
    List<OrderEntity> entities = List.of(new OrderEntity());
    List<OrderDetails> details = List.of(new OrderDetails());

    when(orderRepository.findAll()).thenReturn(entities);
    when(orderRepositoryMapper.toOrderDetails(entities)).thenReturn(details);

    List<OrderDetails> result = orderService.getAllOrders();
    assertEquals(details, result);
  }

  @Test
  void testGetOrderByNumber() {
    UUID orderNumber = UUID.randomUUID();
    OrderEntity entity = new OrderEntity();
    OrderDetails details = new OrderDetails();

    when(orderRepository.findByNaturalId(orderNumber)).thenReturn(Optional.of(entity));
    when(orderRepositoryMapper.toOrderDetails(entity)).thenReturn(details);

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
    UUID productReference = UUID.randomUUID();
    OrderDetails orderDetails = new OrderDetails();
    orderDetails.setOrderNumber(orderNumber);
    orderDetails.setOrderEntries(new ArrayList<>());

    CosmoCatEntity cosmoCatEntity = new CosmoCatEntity();
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setOrderNumber(orderNumber);
    orderEntity.setCosmoCat(cosmoCatEntity);
    orderEntity.setOrderEntries(new ArrayList<>());

    when(cosmoCatRepository.findByNaturalId(catReference)).thenReturn(Optional.of(cosmoCatEntity));
    when(productRepository.findByNaturalId(productReference)).thenReturn(Optional.of(new ProductEntity()));
    when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
    when(orderRepositoryMapper.toOrderDetails(any(OrderEntity.class))).thenReturn(orderDetails);
    when(orderRepositoryMapper.toOrderEntity(any(OrderDetails.class))).thenReturn(orderEntity);

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
