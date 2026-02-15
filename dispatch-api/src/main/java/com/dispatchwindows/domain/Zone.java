package com.dispatchwindows.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "zones")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Zone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "zone_postal_codes", joinColumns = @JoinColumn(name = "zone_id"))
  @Column(name = "postal_code", nullable = false)
  @Builder.Default
  private Set<String> postalCodes = new HashSet<>();
}
