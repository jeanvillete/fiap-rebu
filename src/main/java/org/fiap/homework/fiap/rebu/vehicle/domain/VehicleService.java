package org.fiap.homework.fiap.rebu.vehicle.domain;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    Optional<List<Vehicle>> listAll();

}
