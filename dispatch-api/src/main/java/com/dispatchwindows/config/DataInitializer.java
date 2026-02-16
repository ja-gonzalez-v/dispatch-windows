package com.dispatchwindows.config;

import com.dispatchwindows.domain.*;
import com.dispatchwindows.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            ZoneRepository zoneRepository,
            DeliveryDateAvailabilityRepository availabilityRepository,
            TimeSlotRepository timeSlotRepository) {
        return args -> {

            if (zoneRepository.count() > 0) {
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

            List<Zone> zones = List.of(centro, nunoa, providencia);

            for (Zone zone : zones) {
                for (int i = 1; i <= 3; i++) {

                    DeliveryDateAvailability availability = availabilityRepository.save(
                            DeliveryDateAvailability.builder()
                                    .zone(zone)
                                    .date(LocalDate.now().plusDays(i))
                                    .available(true)
                                    .build());

                    createSlots(availability, timeSlotRepository);
                }
            }

            System.out.println("✔ initial data created");
        };
    }

    private Zone createZone(String name, Set<String> postalCodes) {
        return Zone.builder()
                .name(name)
                .postalCodes(postalCodes)
                .build();
    }

    private void createSlots(
            DeliveryDateAvailability availability,
            TimeSlotRepository repository) {

        repository.save(
                TimeSlot.builder()
                        .availability(availability)
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(12, 0))
                        .capacity(3)
                        .price(BigDecimal.valueOf(4990))
                        .build());

        repository.save(
                TimeSlot.builder()
                        .availability(availability)
                        .startTime(LocalTime.of(13, 0))
                        .endTime(LocalTime.of(16, 0))
                        .capacity(2)
                        .price(BigDecimal.valueOf(5990))
                        .build());

        repository.save(
                TimeSlot.builder()
                        .availability(availability)
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(20, 0))
                        .capacity(1)
                        .price(BigDecimal.valueOf(6990))
                        .build());
    }
}
