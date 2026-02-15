package com.dispatchwindows.repository;

import com.dispatchwindows.domain.DeliveryDateAvailability;
import com.dispatchwindows.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DeliveryDateAvailabilityRepository extends JpaRepository<DeliveryDateAvailability, Long> {
  List<DeliveryDateAvailability> findByZoneAndAvailableIsTrueAndDateGreaterThanEqualOrderByDateAsc(Zone zone, LocalDate from);
  Optional<DeliveryDateAvailability> findByZoneAndDate(Zone zone, LocalDate date);
}
