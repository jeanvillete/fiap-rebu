package org.fiap.homework.fiap.rebu.vehicle.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "open_date_time", nullable = false)
    private LocalDateTime openDateTime;

    @Column(name = "close_date_time")
    private LocalDateTime closeDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Repair() {
    }

    public Repair(LocalDateTime openDateTime, Vehicle vehicle) {
        this.openDateTime = openDateTime;
        this.vehicle = vehicle;
    }

    public Repair closeRepair() {
        this.closeDateTime = LocalDateTime.now();

        return this;
    }

    public LocalDateTime getCloseDateTime() {
        return closeDateTime;
    }
}
