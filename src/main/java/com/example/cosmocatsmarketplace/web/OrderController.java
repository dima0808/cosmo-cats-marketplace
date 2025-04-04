package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.domain.OrderDetails;
import com.example.cosmocatsmarketplace.dto.OrderDto;
import com.example.cosmocatsmarketplace.dto.OrderListDto;
import com.example.cosmocatsmarketplace.service.OrderService;
import com.example.cosmocatsmarketplace.service.mapper.OrderServiceMapper;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final OrderServiceMapper orderServiceMapper;

  @GetMapping
  @PreAuthorize("hasRole('COSMO_ADMIN')")
  public ResponseEntity<OrderListDto> getAllOrders() {
    return ResponseEntity.ok(orderServiceMapper.toOrderListDto(orderService.getAllOrders()));
  }

  @GetMapping("{orderNumber}")
  public ResponseEntity<OrderDto> getOrderByNumber(@PathVariable UUID orderNumber) {
    return ResponseEntity.ok(orderServiceMapper.toOrderDto(orderService.getOrderByNumber(orderNumber)));
  }

  @GetMapping("/by-cat/{catReference}")
  public ResponseEntity<OrderListDto> getAllOrdersByCat(@PathVariable UUID catReference) {
    return ResponseEntity.ok(orderServiceMapper.toOrderListDto(
        orderService.getAllOrdersByCatReference(catReference)));
  }

  @PostMapping("/by-cat/{catReference}")
  public ResponseEntity<OrderDto> addOrder(@PathVariable UUID catReference,
      @RequestBody @Valid OrderDto orderDto) {
    OrderDetails orderDetails = orderService.saveOrder(
        catReference, orderServiceMapper.toOrderDetails(orderDto));
    return ResponseEntity.status(HttpStatus.CREATED).body(orderServiceMapper.toOrderDto(orderDetails));
  }

  @DeleteMapping("{orderNumber}")
  public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderNumber) {
    orderService.deleteOrderByNumber(orderNumber);
    return ResponseEntity.noContent().build();
  }
}
