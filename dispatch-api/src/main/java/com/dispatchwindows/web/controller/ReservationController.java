package com.dispatchwindows.web.controller;

import com.dispatchwindows.domain.Reservation;
import com.dispatchwindows.service.ReservationService;
import com.dispatchwindows.web.dto.CreateReservationRequest;
import com.dispatchwindows.web.dto.ReservationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@RequestBody @Valid CreateReservationRequest request) {
        Reservation reservation = reservationService.confirmReservation(request.orderId(), request.addressLine(),
                request.postalCode(), request.timeSlotId());
        return new ReservationResponse(reservation.getId(), reservation.getOrderId(), reservation.getStatus().name());
    }
}
