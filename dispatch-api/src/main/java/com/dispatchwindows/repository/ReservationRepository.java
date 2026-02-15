package com.dispatchwindows.repository;

import com.dispatchwindows.domain.Reservation;
import com.dispatchwindows.domain.ReservationStatus;
import com.dispatchwindows.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  long countByTimeSlotAndStatus(TimeSlot timeSlot, ReservationStatus status);
}
