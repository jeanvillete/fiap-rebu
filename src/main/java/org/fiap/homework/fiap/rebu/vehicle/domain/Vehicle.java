package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.fiap.homework.fiap.rebu.location.domain.Location;
import org.fiap.homework.fiap.rebu.userdetails.domain.Trip;

import javax.persistence.*;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 7)
    private String plate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private Set<Repair> repairs;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private Set<Trip> trips;

    public Vehicle() {
    }

    public Vehicle(String plate, Location location) {
        this.plate = plate;
        this.location = location;
    }

    public Optional<String> getPlate() {
        return Optional.ofNullable(
                plate
        );
    }

    public Optional<Location> getLocation() {
        return Optional.ofNullable(
                location
        );
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Repair> getRepairs() {
        return Collections.unmodifiableSet(repairs);
    }

    public Set<Trip> getTrips() {
        return Collections.unmodifiableSet(trips);
    }
}
