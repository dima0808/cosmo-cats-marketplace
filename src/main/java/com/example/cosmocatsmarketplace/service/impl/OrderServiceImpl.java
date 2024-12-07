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
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final CosmoCatRepository cosmoCatRepository;
  private final GeneralRepositoryMapper orderMapper;
  private final ProductRepository productRepository;

  @Override
  @Transactional(readOnly = true)
  public List<OrderDetails> getAllOrders() {
    return orderMapper.toOrderDetails(orderRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public OrderDetails getOrderByNumber(UUID orderNumber) {
    return orderMapper.toOrderDetails(
        orderRepository.findByNaturalId(orderNumber)
            .orElseThrow(() -> new OrderNotFoundException(orderNumber)));
  }

  @Override
  @Transactional(readOnly = true)
  public List<UUID> getOrderNumbersByCatReference(UUID catReference) {
    return orderRepository.findOrderNumbersByCatReference(catReference);
  }

  @Override
  @Transactional(propagation = Propagation.NESTED)
  public OrderDetails saveOrder(UUID catReference, OrderDetails orderDetails) {
    CosmoCatEntity cosmoCatEntity = cosmoCatRepository.findByNaturalId(catReference)
        .orElseThrow(() -> new CosmoCatNotFoundException(catReference));
    OrderEntity orderEntity = orderMapper.toOrderEntity(orderDetails);
    List<OrderEntryEntity> orderEntryEntityList = orderEntity.getOrderEntries().stream()
        .peek(orderEntryEntity -> {
          Long productId = orderEntryEntity.getProduct().getId();
          ProductEntity productEntity = productRepository.findById(productId)
              .orElseThrow(() -> new ProductNotFoundException(productId));
          orderEntryEntity.setProduct(productEntity);
          orderEntryEntity.setOrder(orderEntity);
        })
        .toList();
    orderEntity.setOrderEntries(orderEntryEntityList);
    orderEntity.setCosmoCat(cosmoCatEntity);
    return orderMapper.toOrderDetails(orderRepository.save(orderEntity));
  }

  @Override
  @Transactional
  public void deleteOrderByNumber(UUID orderNumber) {
    getOrderByNumber(orderNumber);
    orderRepository.deleteByNaturalId(orderNumber);
  }
}
