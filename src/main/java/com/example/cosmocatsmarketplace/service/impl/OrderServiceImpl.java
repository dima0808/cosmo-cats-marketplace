package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.OrderDetails;
import com.example.cosmocatsmarketplace.repository.CosmoCatRepository;
import com.example.cosmocatsmarketplace.repository.OrderRepository;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntryEntity;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.repository.mapper.GeneralRepositoryMapper;
import com.example.cosmocatsmarketplace.service.OrderService;
import com.example.cosmocatsmarketplace.service.exception.CosmoCatNotFoundException;
import com.example.cosmocatsmarketplace.service.exception.OrderNotFoundException;
import com.example.cosmocatsmarketplace.service.exception.ProductNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final CosmoCatRepository cosmoCatRepository;
  private final GeneralRepositoryMapper orderMapper;
  private final ProductRepository productRepository;

  @Override
  public List<OrderDetails> getAllOrders() {
    return orderMapper.toOrderDetails(orderRepository.findAll());
  }

  @Override
  public OrderDetails getOrderByNumber(UUID orderNumber) {
    return orderMapper.toOrderDetails(
        orderRepository.findByNaturalId(orderNumber)
            .orElseThrow(() -> new OrderNotFoundException(orderNumber)));
  }

  @Override
  public List<UUID> getOrderNumbersByCatReference(UUID catReference) {
    return orderRepository.findOrderNumbersByCatReference(catReference);
  }

  @Override
  public OrderDetails saveOrder(UUID catReference, OrderDetails orderDetails) {
    CosmoCatEntity cosmoCatEntity = cosmoCatRepository.findByNaturalId(catReference)
        .orElseThrow(() -> new CosmoCatNotFoundException(catReference));
    OrderEntity orderEntity = OrderEntity.builder()
        .orderNumber(orderDetails.getOrderNumber())
        .cosmoCat(cosmoCatEntity)
        .build();
    List<OrderEntryEntity> orderEntryEntityList = orderDetails.getOrderEntries().stream()
        .map(orderEntryDetails -> {
          Long productId = orderEntryDetails.getProduct().getId();
          ProductEntity productEntity = productRepository.findById(productId)
              .orElseThrow(() -> new ProductNotFoundException(productId));
          return OrderEntryEntity.builder()
              .quantity(orderEntryDetails.getQuantity())
              .product(productEntity)
              .order(orderEntity)
              .build();
        })
        .toList();
    orderEntity.setOrderEntries(orderEntryEntityList);
    return orderMapper.toOrderDetails(orderRepository.save(orderEntity));
  }

  @Override
  public void deleteOrderByNumber(UUID orderNumber) {
    getOrderByNumber(orderNumber);
    orderRepository.deleteByNaturalId(orderNumber);
  }
}
