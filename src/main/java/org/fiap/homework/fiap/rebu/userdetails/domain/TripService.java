package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;

public interface TripService {
    void validateFromLocationAndToLocation(String fromLocation, String toLocation) throws InvalidSuppliedDataException;

    void save(Trip trip);
}
