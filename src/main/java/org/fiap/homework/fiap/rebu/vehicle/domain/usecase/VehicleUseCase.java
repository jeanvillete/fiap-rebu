package org.fiap.homework.fiap.rebu.vehicle.domain.usecase;

import org.fiap.homework.fiap.rebu.location.domain.Location;
import org.fiap.homework.fiap.rebu.vehicle.domain.Vehicle;
import org.fiap.homework.fiap.rebu.vehicle.domain.VehicleService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleUseCase {

    public static final class VehiclePayload {
        String plate;
        String location;

        public VehiclePayload() {
        }

        public VehiclePayload(Vehicle vehicle) {
            this.plate = vehicle.getPlate().orElse("");
            this.location = vehicle.getLocation()
                    .map(Location::getAddress)
                    .map(address -> address.orElse(""))
                    .orElse("");
        }

        public String getPlate() {
            return plate;
        }

        public String getLocation() {
            return location;
        }
    }

    private final VehicleService vehicleService;

    public VehicleUseCase(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public List<VehiclePayload> listVehicles() {
        return vehicleService.listAll()
                .orElse(Collections.emptyList())
                .stream()
                .map(VehiclePayload::new)
                .collect(Collectors.toList());
    }
}
