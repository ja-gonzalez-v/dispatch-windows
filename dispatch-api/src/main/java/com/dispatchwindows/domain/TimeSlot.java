package com.dispatchwindows.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(
  name = "time_slots",
  uniqueConstraints = @UniqueConstraint(
    name = "uk_date_start_end",
    columnNames = {"availability_id", "start_time", "end_time"}
  )
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TimeSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "availability_id", nullable = false)
  private DeliveryDateAvailability availability;

  @Column(name = "start_time", nullable = false)
  private LocalTime startTime;

  @Column(name = "end_time", nullable = false)
  private LocalTime endTime;

  @Column(nullable = false)
  private int capacity;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal price;

  @Version
  private Long version;
}
