package com.dispatchwindows.service;

import com.dispatchwindows.web.dto.AvailableDateDto;
import com.dispatchwindows.web.dto.TimeSlotDto;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {

  List<AvailableDateDto> getAvailableDates(String postalCode);

  List<TimeSlotDto> getTimeSlots(String postalCode, LocalDate date);
}
