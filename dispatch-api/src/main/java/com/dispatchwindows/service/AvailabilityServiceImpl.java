package com.dispatchwindows.service;

import com.dispatchwindows.domain.*;
import com.dispatchwindows.repository.*;
import com.dispatchwindows.web.dto.*;
import com.dispatchwindows.web.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

	private final GeoService geoService;
	private final DeliveryDateAvailabilityRepository availabilityRepository;
	private final TimeSlotRepository timeSlotRepository;
	private final ReservationRepository reservationRepository;

	public AvailabilityServiceImpl(
			GeoService geoService,
			DeliveryDateAvailabilityRepository availabilityRepository,
			TimeSlotRepository timeSlotRepository,
			ReservationRepository reservationRepository) {
		this.geoService = geoService;
		this.availabilityRepository = availabilityRepository;
		this.timeSlotRepository = timeSlotRepository;
		this.reservationRepository = reservationRepository;
	}

	@Override
	public List<AvailableDateDto> getAvailableDates(String postalCode) {

		Zone zone = geoService.resolveZoneByPostalCode(postalCode);

		return availabilityRepository
				.findByZoneAndAvailableIsTrueAndDateGreaterThanEqualOrderByDateAsc(
						zone, LocalDate.now())
				.stream()
				.map(a -> new AvailableDateDto(a.getDate()))
				.toList();
	}

	@Override
	public List<TimeSlotDto> getTimeSlots(String postalCode, LocalDate date) {

		Zone zone = geoService.resolveZoneByPostalCode(postalCode);

		DeliveryDateAvailability availability = availabilityRepository
				.findByZoneAndDate(zone, date)
				.orElseThrow(() -> new NotFoundException("No availability for date " + date));

		return timeSlotRepository.findByAvailabilityId(availability.getId())
				.stream()
				.map(slot -> {

					long reserved = reservationRepository.countByTimeSlotAndStatus(
							slot, ReservationStatus.RESERVED);

					int remaining = Math.max(0, slot.getCapacity() - (int) reserved);

					return new TimeSlotDto(
							slot.getId(),
							slot.getStartTime(),
							slot.getEndTime(),
							slot.getPrice(),
							remaining);
				})
				.filter(dto -> dto.remainingCapacity() > 0)
				.toList();
	}
}
