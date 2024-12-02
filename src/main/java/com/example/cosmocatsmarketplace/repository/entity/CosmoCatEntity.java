package com.example.cosmocatsmarketplace.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "cosmo_cat")
public class CosmoCatEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NaturalId
  @Column(nullable = false, unique = true)
  UUID catReference;

  @Column(nullable = false)
  String name;

  @Column(unique = true, nullable = false)
  String email;

  String address;
  String phoneNumber;

  @OneToMany(mappedBy = "cosmoCat", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
  List<OrderEntity> orders;
}
