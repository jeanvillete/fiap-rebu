package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    @Override
    public Optional<Vehicle> getVehicleByPlate(String plate) {
        return vehicleRepository.findByPlate(plate);
    }

    @Override
    public Optional<Vehicle> retrieveAnyAvailableVehicle() {
        return vehicleRepository.retrieveAnyAvailableVehicle()
                .map(List::stream)
                .map(Stream::findAny)
                .orElse(Optional.empty());
    }

    @Override
    public void checkPlateIsAlreadyInUse(String plate) throws InvalidSuppliedDataException {
        Integer countPlate = vehicleRepository.countByPlate(plate)
            .orElse(0);

        if (countPlate > 0) {
            throw new InvalidSuppliedDataException("There is already a vehicle recorded with the plate \"" + plate + "\"");
        }
    }

    @Override
    public void vehicleAvailableForRepairing(Vehicle vehicle) throws InvalidSuppliedDataException {
        boolean anyCurrentRepairForVehicle = vehicle.getRepairs()
                .stream()
                .filter(repair -> repair.getCloseDateTime() == null)
                .findAny()
                .isPresent();

        if (anyCurrentRepairForVehicle) {
            throw new InvalidSuppliedDataException(
                    "The vehicle with plate \"" + vehicle.getPlate() + "\" is already on repairing."
            );
        }
    }
}
