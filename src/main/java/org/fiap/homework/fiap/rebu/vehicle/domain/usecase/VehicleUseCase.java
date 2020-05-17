package org.fiap.homework.fiap.rebu.vehicle.domain.usecase;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.location.domain.Location;
import org.fiap.homework.fiap.rebu.location.domain.LocationService;
import org.fiap.homework.fiap.rebu.vehicle.domain.Repair;
import org.fiap.homework.fiap.rebu.vehicle.domain.RepairService;
import org.fiap.homework.fiap.rebu.vehicle.domain.Vehicle;
import org.fiap.homework.fiap.rebu.vehicle.domain.VehicleService;
import org.fiap.homework.fiap.rebu.vehicle.domain.exception.VehicleNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleUseCase {

    public enum VehicleStatusPayload {
        REPAIRING, ON_A_TRIP, AVAILABLE, ON_THE_WAY_TO_GET_PASSENGER;

        static VehicleStatusPayload valueOf(Vehicle vehicle) {
            boolean anyOpenRepairForVehicle = vehicle.getRepairs()
                    .stream()
                    .filter(repair -> repair.getCloseDateTime() == null)
                    .findAny()
                    .isPresent();

            if (anyOpenRepairForVehicle) {
                return REPAIRING;
            }

            boolean anyOpenOnBoardTrip = vehicle.getTrips()
                    .stream()
                    .filter(trip -> trip.getBoardingDateTime() == null)
                    .findAny()
                    .isPresent();

            if (anyOpenOnBoardTrip) {
                return ON_THE_WAY_TO_GET_PASSENGER;
            }

            boolean anyOpenNotFinishedTrip = vehicle.getTrips()
                    .stream()
                    .filter(trip -> trip.getLandingDateTime() == null)
                    .findAny()
                    .isPresent();

            if (anyOpenNotFinishedTrip) {
                return ON_A_TRIP;
            }

            return AVAILABLE;
        }
    }

    public static final class VehiclePayload {
        String plate;
        String location;
        VehicleStatusPayload status;

        public VehiclePayload() {
        }

        public VehiclePayload(String plate) {
            this.plate = plate;
        }

        VehiclePayload(Vehicle vehicle) {
            this.plate = vehicle.getPlate().orElse("");
            this.location = vehicle.getLocation()
                    .map(Location::getAddress)
                    .map(address -> address.orElse(""))
                    .orElse("");
            this.status = VehicleStatusPayload.valueOf(vehicle);
        }

        public String getPlate() {
            return plate;
        }

        public String getLocation() {
            return location;
        }

        public VehicleStatusPayload getStatus() {
            return status;
        }
    }

    private final VehicleService vehicleService;
    private final LocationService locationService;
    private final RepairService repairService;

    public VehicleUseCase(VehicleService vehicleService, LocationService locationService, RepairService repairService) {
        this.vehicleService = vehicleService;
        this.locationService = locationService;
        this.repairService = repairService;
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

        this.vehicleService.checkPlateIsAlreadyInUse(vehiclePayload.plate);

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

    public void putVehicleOnRepairing(VehiclePayload vehiclePayload) throws VehicleNotFoundException, InvalidSuppliedDataException {
        String vehiclePlate = vehiclePayload.plate;

        Vehicle vehicle = vehicleService.getVehicleByPlate(vehiclePlate)
                .orElseThrow(() -> new VehicleNotFoundException("No vehicle was found for the given plate"));

        vehicleService.vehicleAvailableForRepairing(vehicle);

        Repair repair = new Repair(
                LocalDateTime.now(),
                vehicle
        );
        repairService.save(repair);
    }

    public void concludeRepairingForVehicle(VehiclePayload vehiclePayload) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleService.getVehicleByPlate(vehiclePayload.plate)
                .orElseThrow(() -> new VehicleNotFoundException("No vehicle was found for the given plate"));

        Repair repair = repairService.getRepairByVehicle(vehicle)
                .orElseThrow(() -> new VehicleNotFoundException("No vehicle was found for the given plate"))
                .closeRepair();

        repairService.save(repair);
    }
}
