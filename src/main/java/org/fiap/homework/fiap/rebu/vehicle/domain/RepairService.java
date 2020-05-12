package org.fiap.homework.fiap.rebu.vehicle.domain;

import java.util.Optional;

public interface RepairService {
    void save(Repair repair);

    Optional<Repair> getRepairByVehicle(Vehicle vehicle);
}
