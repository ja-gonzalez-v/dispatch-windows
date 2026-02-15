package com.dispatchwindows.repository;

import com.dispatchwindows.domain.TimeSlot;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

  @Query("select ts from TimeSlot ts where ts.availability.id = :availabilityId order by ts.startTime asc")
  List<TimeSlot> findByAvailabilityId(@Param("availabilityId") Long availabilityId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select ts from TimeSlot ts where ts.id = :id")
  Optional<TimeSlot> findByIdForUpdate(@Param("id") Long id);
}
