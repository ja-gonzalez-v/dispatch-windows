package com.dispatchwindows.config;

import com.dispatchwindows.domain.*;
import com.dispatchwindows.repository.*;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer {

    private final ZoneRepository zoneRepository;
    private final DeliveryDateAvailabilityRepository availabilityRepository;
    private final TimeSlotRepository timeSlotRepository;

    public DataInitializer(
            ZoneRepository zoneRepository,
            DeliveryDateAvailabilityRepository availabilityRepository,
            TimeSlotRepository timeSlotRepository) {
        this.zoneRepository = zoneRepository;
        this.availabilityRepository = availabilityRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        System.out.println(">>> DataInitializer");

        if (zoneRepository.count() > 0) {
            System.out.println(">>> Datos ya existen, se omite seed");
            return;
        }

        Zone centro = createZone(
                "Santiago Centro",
                Set.of("7500000", "7500001", "7500002"));

        Zone nunoa = createZone(
                "Ñuñoa",
                Set.of("7750000", "7750001", "7750002"));

        Zone providencia = createZone(
                "Providencia",
                Set.of("7510000", "7510001", "7510002"));

        zoneRepository.saveAll(List.of(centro, nunoa, providencia));

        for (Zone zone : List.of(centro, nunoa, providencia)) {
            for (int i = 1; i <= 3; i++) {

                DeliveryDateAvailability availability = availabilityRepository.save(
                        DeliveryDateAvailability.builder()
                                .zone(zone)
                                .date(LocalDate.now().plusDays(i))
                                .available(true)
                                .build());

                createSlots(availability);
            }
        }

        System.out.println("initial data created");
    }

    private Zone createZone(String name, Set<String> postalCodes) {
        return Zone.builder()
                .name(name)
                .postalCodes(postalCodes)
                .build();
    }

    private void createSlots(DeliveryDateAvailability availability) {

        timeSlotRepository.save(
                TimeSlot.builder()
                        .availability(availability)
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(12, 0))
                        .capacity(3)
                        .price(BigDecimal.valueOf(4990))
                        .build());

        timeSlotRepository.save(
                TimeSlot.builder()
                        .availability(availability)
                        .startTime(LocalTime.of(13, 0))
                        .endTime(LocalTime.of(16, 0))
                        .capacity(2)
                        .price(BigDecimal.valueOf(5990))
                        .build());

        timeSlotRepository.save(
                TimeSlot.builder()
                        .availability(availability)
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(20, 0))
                        .capacity(1)
                        .price(BigDecimal.valueOf(6990))
                        .build());
    }
}
