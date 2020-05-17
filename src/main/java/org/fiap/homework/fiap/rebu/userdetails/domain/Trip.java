package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.location.domain.Location;
import org.fiap.homework.fiap.rebu.vehicle.domain.Vehicle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 15)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "from_location_id")
    private Location fromLocation;

    @OneToOne
    @JoinColumn(name = "to_location_id")
    private Location toLocation;

    @Column(name = "request_date_time")
    private LocalDateTime requestDateTime;

    @Column(name = "boarding_date_time")
    private LocalDateTime boardingDateTime;

    @Column(name = "landing_date_time")
    private LocalDateTime landingDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Trip() {
    }

    public static Trip newTrip(User user, Location fromLocation, Location toLocation, Vehicle vehicle) {
        Trip newTrip = new Trip();
        newTrip.uuid = UUID.randomUUID().toString().substring(0, 15);
        newTrip.user = user;
        newTrip.requestDateTime = LocalDateTime.now();
        newTrip.fromLocation = fromLocation;
        newTrip.toLocation = toLocation;
        newTrip.vehicle = vehicle;

        return newTrip;
    }

    public String getUuid() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public LocalDateTime getBoardingDateTime() {
        return boardingDateTime;
    }

    public LocalDateTime getLandingDateTime() {
        return landingDateTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
