package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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

    @Override
    public void validPlate(String plate) throws InvalidSuppliedDataException {
        if (!Pattern.compile("\\w{3}\\d{4}").matcher(plate).matches()) {
            throw new InvalidSuppliedDataException("The plate provided must be formatted as AAA0000.");
        }
    }

    @Override
    public void validLocation(String location) throws InvalidSuppliedDataException {
        if (location == null || location.length() < 10) {
            throw new InvalidSuppliedDataException("Location must be at least 10 characters.");
        }
    }

    @Override
    public void save(Vehicle vehicle) {
        this.vehicleRepository.save(vehicle);
    }
}
