package com.example.cosmocatsmarketplace.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "order_entry")
public class OrderEntryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_entry_id_seq")
  @SequenceGenerator(name = "order_entry_id_seq", sequenceName = "order_entry_id_seq")
  private Long id;

  @Column(nullable = false)
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;
}
