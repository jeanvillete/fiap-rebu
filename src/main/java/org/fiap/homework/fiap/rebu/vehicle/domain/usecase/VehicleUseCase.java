package org.fiap.homework.fiap.rebu.vehicle.domain.usecase;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.location.domain.Location;
import org.fiap.homework.fiap.rebu.location.domain.LocationService;
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

        VehiclePayload(Vehicle vehicle) {
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
    private final LocationService locationService;

    public VehicleUseCase(VehicleService vehicleService, LocationService locationService) {
        this.vehicleService = vehicleService;
        this.locationService = locationService;
    }

    public List<VehiclePayload> listVehicles() {
        return vehicleService.listAll()
                .orElse(Collections.emptyList())
                .stream()
                .map(VehiclePayload::new)
                .collect(Collectors.toList());
    }

    public void saveVehicle(VehiclePayload vehiclePayload) throws InvalidSuppliedDataException {
        this.vehicleService.validPlate(vehiclePayload.plate);
        this.vehicleService.validLocation(vehiclePayload.location);


        Location location = new Location(
                vehiclePayload.location
        );
        this.locationService.save(location);

        Vehicle vehicle = new Vehicle(
                vehiclePayload.plate,
                location
        );
        this.vehicleService.save(vehicle);
    }
}
