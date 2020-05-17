package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface TripRepository extends JpaRepository<Trip, Integer> {

    @Query(value = "SELECT trip FROM Trip trip JOIN trip.user usr WHERE trip.uuid = :tripUUID AND usr.nickname = :nickname")
    Optional<Trip> findByUUIDAndUserNickname(String tripUUID, String nickname);

}
