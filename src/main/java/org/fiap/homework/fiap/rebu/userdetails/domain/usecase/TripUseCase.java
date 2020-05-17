package org.fiap.homework.fiap.rebu.userdetails.domain.usecase;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.location.domain.Location;
import org.fiap.homework.fiap.rebu.location.domain.LocationService;
import org.fiap.homework.fiap.rebu.userdetails.domain.Trip;
import org.fiap.homework.fiap.rebu.userdetails.domain.TripService;
import org.fiap.homework.fiap.rebu.userdetails.domain.User;
import org.fiap.homework.fiap.rebu.userdetails.domain.UserService;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.*;
import org.fiap.homework.fiap.rebu.vehicle.domain.Vehicle;
import org.fiap.homework.fiap.rebu.vehicle.domain.VehicleService;
import org.springframework.stereotype.Component;

@Component
public class TripUseCase {

    public static final class TripPayload {
        String uuid;
        String fromLocation;
        String toLocation;

        public TripPayload() {
        }

        public TripPayload(Trip trip) {
            this.uuid = trip.getUuid();
            this.fromLocation = trip.getFromLocation().getAddress().orElse("");
            this.toLocation = trip.getToLocation().getAddress().orElse("");
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getFromLocation() {
            return fromLocation;
        }

        public void setFromLocation(String fromLocation) {
            this.fromLocation = fromLocation;
        }

        public String getToLocation() {
            return toLocation;
        }

        public void setToLocation(String toLocation) {
            this.toLocation = toLocation;
        }
    }

    private final UserService userService;
    private final TripService tripService;
    private final LocationService locationService;
    private final VehicleService vehicleService;

    public TripUseCase(UserService userService, TripService tripService, LocationService locationService, VehicleService vehicleService) {
        this.userService = userService;
        this.tripService = tripService;
        this.locationService = locationService;
        this.vehicleService = vehicleService;
    }

    public TripPayload newTrip(String userNickname, TripPayload tripPayload) throws InvalidSuppliedDataException, NoCarAvailableForTrip, UserHasOpenTrip {
        User user = userService.findUserByNickname(userNickname)
                .orElseThrow(() ->
                        new InvalidSuppliedDataException("No user found for the provided nickname; " + userNickname)
                );

        userService.ensureThereIsNoOpenTripForTheUser(user);

        tripService.validateFromLocationAndToLocation(tripPayload.fromLocation, tripPayload.toLocation);

        Location fromLocation = new Location(tripPayload.fromLocation);
        locationService.save(fromLocation);

        Location toLocation = new Location(tripPayload.fromLocation);
        locationService.save(toLocation);

        Vehicle vehicle = vehicleService.retrieveAnyAvailableVehicle()
                .orElseThrow(() ->
                    new NoCarAvailableForTrip("No available car could be retrieved for a trip at this moment.")
                );

        vehicle.setLocation(fromLocation);
        vehicleService.save(vehicle);

        Trip newTrip = Trip.newTrip(
                user,
                fromLocation,
                toLocation,
                vehicle
        );
        tripService.save(newTrip);

        return new TripPayload(newTrip);
    }

    public void markATripAsOnBoarded(String userNickname, String tripUUID) throws InvalidSuppliedDataException, TripAlreadyOnBoarded {
        User user = userService.findUserByNickname(userNickname)
                .orElseThrow(() ->
                        new InvalidSuppliedDataException("No user found for the provided nickname; " + userNickname)
                );

        Trip trip = tripService.findByUserNicknameAndTripUUID(user.getNickname(), tripUUID)
                .orElseThrow(() ->
                        new InvalidSuppliedDataException(
                                "No trip could found for the provided nickname [" + user.getNickname() + "] and " +
                                        "trip uuid [" + tripUUID + "]"
                        )
                );

        tripService.ensureTripAvailableForOnBoard(trip);

        trip.recordOnBoardDateTime();

        Vehicle vehicle = trip.getVehicle();
        vehicle.setLocation(trip.getFromLocation());
        vehicleService.save(vehicle);

        tripService.save(trip);
    }

    public void markATripAsFinished(String userNickname, String tripUUID) throws InvalidSuppliedDataException, TripAlreadyFinished, TripIsNotYetOnBoarded {
        User user = userService.findUserByNickname(userNickname)
                .orElseThrow(() ->
                        new InvalidSuppliedDataException("No user found for the provided nickname; " + userNickname)
                );

        Trip trip = tripService.findByUserNicknameAndTripUUID(user.getNickname(), tripUUID)
                .orElseThrow(() ->
                        new InvalidSuppliedDataException(
                                "No trip could found for the provided nickname [" + user.getNickname() + "] and " +
                                        "trip uuid [" + tripUUID + "]"
                        )
                );

        tripService.ensureTripIsOnBoarded(trip);
        tripService.ensureTripIsNotFinished(trip);

        trip.recordLandingDateTime();

        Vehicle vehicle = trip.getVehicle();
        vehicle.setLocation(trip.getToLocation());
        vehicleService.save(vehicle);

        tripService.save(trip);
    }
}
