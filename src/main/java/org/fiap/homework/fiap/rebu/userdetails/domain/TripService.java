package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.TripAlreadyFinished;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.TripAlreadyOnBoarded;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.TripIsNotYetOnBoarded;

import java.util.Optional;

public interface TripService {
    void validateFromLocationAndToLocation(String fromLocation, String toLocation) throws InvalidSuppliedDataException;

    void save(Trip trip);

    Optional<Trip> findByUserNicknameAndTripUUID(String nickname, String tripUUID);

    void ensureTripAvailableForOnBoard(Trip trip) throws TripAlreadyOnBoarded;

    void ensureTripIsNotFinished(Trip trip) throws TripAlreadyFinished;

    void ensureTripIsOnBoarded(Trip trip) throws TripIsNotYetOnBoarded;
}
