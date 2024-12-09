package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.OrderDetails;
import java.util.List;
import java.util.UUID;

public interface OrderService {

  List<OrderDetails> getAllOrders();

  List<OrderDetails> getAllOrdersByCatReference(UUID catReference);

  OrderDetails getOrderByNumber(UUID orderNumber);

  List<UUID> getOrderNumbersByCatReference(UUID catReference);

  OrderDetails saveOrder(UUID catReference, OrderDetails orderDetails);

  void deleteOrderByNumber(UUID orderNumber);
}
