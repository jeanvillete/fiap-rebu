package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface RepairRepository extends JpaRepository<Repair, Integer> {

    @Query(value = "SELECT repair FROM Repair repair WHERE repair.id in ( SELECT MAX( id ) FROM Repair WHERE vehicle = :vehicle )")
    Optional<Repair> findByVehicle(Vehicle vehicle);

}
