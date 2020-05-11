package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.fiap.homework.fiap.rebu.location.domain.Location;

import javax.persistence.*;
import java.util.Optional;

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

    public Vehicle() {
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
}
