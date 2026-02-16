package com.dispatchwindows.repository;

import com.dispatchwindows.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

  Optional<Zone> findByName(String name);

  @Query("""
        select z
        from Zone z
        join z.postalCodes pc
        where pc = :postalCode
      """)
  Optional<Zone> findByPostalCode(@Param("postalCode") String postalCode);
}
