package com.dispatchwindows.service;

import com.dispatchwindows.domain.*;
import com.dispatchwindows.repository.*;
import com.dispatchwindows.web.error.NotFoundException;
import com.dispatchwindows.web.error.SlotSoldOutException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final TimeSlotRepository timeSlotRepository;
    private final ReservationRepository reservationRepository;
    private final GeoService geoService;
    private final TimeSlotLockService lockService;

    public ReservationServiceImpl(
            TimeSlotRepository timeSlotRepository,
            ReservationRepository reservationRepository,
            GeoService geoService,
            TimeSlotLockService lockService) {
        this.timeSlotRepository = timeSlotRepository;
        this.reservationRepository = reservationRepository;
        this.geoService = geoService;
        this.lockService = lockService;
    }

    @Override
    @Transactional
    public Reservation confirmReservation(
            String orderId,
            String addressLine,
            String postalCode,
            Long timeSlotId) {

        lockService.validateLock(timeSlotId, orderId);

        Zone zone = geoService.resolveZoneByPostalCode(postalCode);

        TimeSlot slot = timeSlotRepository.findByIdForUpdate(timeSlotId)
                .orElseThrow(() -> new NotFoundException("Time slot not found"));

        long reserved = reservationRepository.countByTimeSlotAndStatus(
                slot, ReservationStatus.RESERVED);

        if (reserved >= slot.getCapacity()) {
            throw new SlotSoldOutException();
        }

        Reservation reservation = Reservation.builder()
                .orderId(orderId)
                .timeSlot(slot)
                .zone(zone)
                .addressLine(addressLine)
                .postalCode(postalCode)
                .status(ReservationStatus.RESERVED)
                .build();

        Reservation saved = reservationRepository.save(reservation);

        lockService.release(timeSlotId, orderId);

        return saved;
    }
}
