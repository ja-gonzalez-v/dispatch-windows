package com.dispatchwindows.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
  name = "reservations",
  indexes = {
    @Index(name = "idx_res_time_slot", columnList = "time_slot_id"),
    @Index(name = "idx_res_order", columnList = "order_id")
  },
  uniqueConstraints = @UniqueConstraint(name = "uk_order_timeslot", columnNames = {"order_id", "time_slot_id"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "order_id", nullable = false)
  private String orderId;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "time_slot_id", nullable = false)
  private TimeSlot timeSlot;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "zone_id", nullable = false)
  private Zone zone;

  @Column(nullable = false)
  private String addressLine;

  @Column(nullable = false)
  private String postalCode;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReservationStatus status;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @PrePersist
  void onCreate() {
    if (createdAt == null) createdAt = Instant.now();
  }
}
