package com.dispatchwindows.service;

import com.dispatchwindows.domain.Zone;
import com.dispatchwindows.repository.ZoneRepository;
import com.dispatchwindows.web.error.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GeoServiceImpl implements GeoService {

  private final ZoneRepository zoneRepository;

  public GeoServiceImpl(ZoneRepository zoneRepository) {
    this.zoneRepository = zoneRepository;
  }

  @Override
  public Zone resolveZoneByPostalCode(String postalCode) {
    return zoneRepository.findAll().stream()
      .filter(zone -> zone.getPostalCodes().contains(postalCode))
      .findFirst()
      .orElseThrow(() ->
        new NotFoundException("No delivery coverage for postal code " + postalCode)
      );
  }
}
