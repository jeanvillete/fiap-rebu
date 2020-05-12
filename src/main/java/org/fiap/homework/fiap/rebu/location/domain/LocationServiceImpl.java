package org.fiap.homework.fiap.rebu.location.domain;

import org.springframework.stereotype.Service;

@Service
class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void save(Location location) {
        this.locationRepository.save(location);
    }
}
