package com.example.cosmocatsmarketplace.web;

import com.example.cosmocatsmarketplace.AbstractIt;
import com.example.cosmocatsmarketplace.common.CategoryType;
import com.example.cosmocatsmarketplace.dto.OrderDto;
import com.example.cosmocatsmarketplace.dto.OrderEntryDto;
import com.example.cosmocatsmarketplace.dto.ProductDto;
import com.example.cosmocatsmarketplace.repository.CosmoCatRepository;
import com.example.cosmocatsmarketplace.repository.OrderRepository;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.CosmoCatEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntryEntity;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Order Controller IT")
class OrderControllerIT extends AbstractIt {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CosmoCatRepository cosmoCatRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductRepository productRepository;

  @SpyBean
  private OrderService orderService;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    reset(orderService);
    orderRepository.deleteAll();
    cosmoCatRepository.deleteAll();
    productRepository.deleteAll();
    createCosmoCatAndProduct();
  }

  @Test
  @SneakyThrows
  void testGetAllOrders() {
    saveOrderEntity();

    mockMvc.perform(get("/api/v1/orders")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testGetOrderByNumber() {
    OrderEntity orderEntity = saveOrderEntity();

    mockMvc.perform(get("/api/v1/orders/{orderNumber}", orderEntity.getOrderNumber())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  void testCreateOrder() {
    CosmoCatEntity cosmoCatEntity = cosmoCatRepository.findAll().stream().findFirst()
        .orElseThrow(() -> new RuntimeException("CosmoCat not found"));

    ProductEntity productEntity = productRepository.findAll().stream().findFirst()
        .orElseThrow(() -> new RuntimeException("Product not found"));

    OrderDto orderDto = new OrderDto();
    orderDto.setOrderEntries(List.of(
        OrderEntryDto.builder()
            .quantity(2)
            .product(ProductDto.builder()
                .productReference(productEntity.getProductReference())
                .build())
            .build()
    ));

    mockMvc.perform(post("/api/v1/orders/by-cat/{catReference}", cosmoCatEntity.getCatReference())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(orderDto)))
        .andExpect(status().isCreated());
  }

  @Test
  @SneakyThrows
  void testDeleteOrder() {
    OrderEntity orderEntity = saveOrderEntity();

    mockMvc.perform(delete("/api/v1/orders/{orderNumber}", orderEntity.getOrderNumber())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  private OrderEntity saveOrderEntity() {
    CosmoCatEntity cosmoCatEntity = cosmoCatRepository.findAll().stream().findFirst()
        .orElseThrow(() -> new RuntimeException("CosmoCat not found"));

    ProductEntity productEntity = productRepository.findAll().stream().findFirst()
        .orElseThrow(() -> new RuntimeException("Product not found"));

    OrderEntity orderEntity = OrderEntity.builder()
        .orderNumber(UUID.randomUUID())
        .cosmoCat(cosmoCatEntity)
        .build();
    OrderEntryEntity orderEntryEntity = OrderEntryEntity.builder()
        .quantity(2)
        .product(productEntity)
        .order(orderEntity)
        .build();
    orderEntity.setOrderEntries(List.of(orderEntryEntity));

    return orderRepository.save(orderEntity);
  }

  private void createCosmoCatAndProduct() {
    cosmoCatRepository.save(CosmoCatEntity.builder()
        .catReference(UUID.fromString("4157aef3-8534-44ca-9968-53624f7d7fe0"))
        .name("Zebulon")
        .email("zebulon@cosmo.cats")
        .address("Andromeda, Sector 42, Alpha Centauri")
        .phoneNumber("123-456-7890")
        .build());

    productRepository.save(ProductEntity.builder()
        .productReference(UUID.randomUUID())
        .name("Test Product")
        .description("Test Description")
        .price(100)
        .categories(List.of(CategoryType.ELECTRONICS))
        .build());
  }
}
