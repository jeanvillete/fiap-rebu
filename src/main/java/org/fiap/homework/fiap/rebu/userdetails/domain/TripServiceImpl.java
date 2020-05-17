package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.TripAlreadyFinished;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.TripAlreadyOnBoarded;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.TripIsNotYetOnBoarded;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Optional<Trip> findByUserNicknameAndTripUUID(String nickname, String tripUUID) {
        return tripRepository.findByUUIDAndUserNickname(tripUUID, nickname);
    }

    @Override
    public void ensureTripAvailableForOnBoard(Trip trip) throws TripAlreadyOnBoarded {
        if (trip.getBoardingDateTime() != null) {
            throw new TripAlreadyOnBoarded(
                    "The trip identified by uuid [" + trip.getUuid() + "] is already recorded as on boarded."
            );
        }
    }

    @Override
    public void ensureTripIsOnBoarded(Trip trip) throws TripIsNotYetOnBoarded {
        if (trip.getBoardingDateTime() == null) {
            throw new TripIsNotYetOnBoarded(
                    "The trip identified by uuid [" + trip.getUuid() + "] is not yet on boarded."
            );
        }
    }

    @Override
    public void ensureTripIsNotFinished(Trip trip) throws TripAlreadyFinished {
        if (trip.getLandingDateTime() != null) {
            throw new TripAlreadyFinished("The trip identified by uuid [" + trip.getUuid() + "] is already finished.");
        }
    }
}
