package com.dispatchwindows.service;

import com.dispatchwindows.domain.Reservation;

public interface ReservationService {

    Reservation confirmReservation(
            String orderId,
            String addressLine,
            String postalCode,
            Long timeSlotId);
}
