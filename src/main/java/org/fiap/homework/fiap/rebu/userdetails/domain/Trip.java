package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.location.domain.Location;
import org.fiap.homework.fiap.rebu.vehicle.domain.Vehicle;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 15)
    private String uuid;

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

    
}
