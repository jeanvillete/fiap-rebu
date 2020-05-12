package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findByPlate(String plate);

}
