package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    Optional<List<Vehicle>> listAll();

    void validPlate(String plate) throws InvalidSuppliedDataException;

    void validLocation(String location) throws InvalidSuppliedDataException;

    void save(Vehicle vehicle);
}
