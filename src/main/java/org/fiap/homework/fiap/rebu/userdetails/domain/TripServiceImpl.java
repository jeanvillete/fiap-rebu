package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.springframework.stereotype.Service;

@Service
class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public void validateFromLocationAndToLocation(String fromLocation, String toLocation) throws InvalidSuppliedDataException {
        if (fromLocation == null || fromLocation.length() < 10) {
            throw new InvalidSuppliedDataException(
                    "The parameter \"fromLocation\" is mandatory and must be greater than or equals 10 characters."
            );
        }
        if (toLocation == null || toLocation.length() < 10) {
            throw new InvalidSuppliedDataException(
                    "The parameter \"toLocation\" is mandatory and must be greater than or equals 10 characters."
            );
        }
    }

    @Override
    public void save(Trip trip) {
        tripRepository.save(trip);
    }
}
