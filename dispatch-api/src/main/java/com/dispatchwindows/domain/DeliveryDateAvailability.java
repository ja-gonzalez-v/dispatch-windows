package com.dispatchwindows.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
  name = "delivery_date_availability",
  uniqueConstraints = @UniqueConstraint(name = "uk_zone_date", columnNames = {"zone_id", "delivery_date"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class DeliveryDateAvailability {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "zone_id", nullable = false)
  private Zone zone;

  @Column(name = "delivery_date", nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  @Builder.Default
  private boolean available = true;
}
