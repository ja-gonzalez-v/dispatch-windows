package com.dispatchwindows.web.controller;

import com.dispatchwindows.service.AvailabilityService;
import com.dispatchwindows.web.dto.AvailableDateDto;
import com.dispatchwindows.web.dto.TimeSlotDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    /**
     * 
     * GET /api/availability/dates?postalCode=7500000
     */
    @GetMapping("/dates")
    public List<AvailableDateDto> getAvailableDates(
            @RequestParam @NotBlank String postalCode) {
        return availabilityService.getAvailableDates(postalCode);
    }

    /**
     *
     * GET /api/availability/timeslots?postalCode=7500000&date=2026-02-20
     */
    @GetMapping("/timeslots")
    public List<TimeSlotDto> getTimeSlots(
            @RequestParam @NotBlank String postalCode,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return availabilityService.getTimeSlots(postalCode, date);
    }
}
