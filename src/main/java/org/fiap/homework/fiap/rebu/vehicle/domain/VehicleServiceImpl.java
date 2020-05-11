package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<List<Vehicle>> listAll() {
        return Optional.ofNullable(
                vehicleRepository.findAll()
        );
    }
}
