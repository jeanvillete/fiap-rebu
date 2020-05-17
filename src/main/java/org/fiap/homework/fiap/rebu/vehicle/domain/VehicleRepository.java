package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findByPlate(String plate);

    @Query(value = "SELECT vhcle FROM Vehicle vhcle LEFT JOIN vhcle.repairs repair LEFT JOIN vhcle.trips trip WHERE (repair IS NULL OR repair.closeDateTime IS NOT NULL) AND (trip IS NULL OR trip.landingDateTime IS NOT NULL)")
    Optional<List<Vehicle>> retrieveAnyAvailableVehicle();

    Optional<Integer> countByPlate(String plate);
}
