package com.dispatchwindows.service;

import com.dispatchwindows.domain.Zone;

public interface GeoService {
  Zone resolveZoneByPostalCode(String postalCode);
}
