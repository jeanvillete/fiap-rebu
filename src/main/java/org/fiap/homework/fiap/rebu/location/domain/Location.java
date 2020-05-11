package org.fiap.homework.fiap.rebu.location.domain;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String address;

    public Location() {
    }

    public Location(String address) {
        this.address = address;
    }

    public Optional<String> getAddress() {
        return Optional.ofNullable(
                address
        );
    }
}
