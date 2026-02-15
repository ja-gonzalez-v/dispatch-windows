package com.dispatchwindows.repository;

import com.dispatchwindows.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
  Optional<Zone> findByName(String name);
}
