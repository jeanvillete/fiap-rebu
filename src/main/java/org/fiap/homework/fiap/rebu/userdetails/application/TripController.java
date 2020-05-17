package org.fiap.homework.fiap.rebu.userdetails.application;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.NoCarAvailableForTrip;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.UserHasOpenTrip;
import org.fiap.homework.fiap.rebu.userdetails.domain.usecase.TripUseCase;
import org.fiap.homework.fiap.rebu.userdetails.domain.usecase.TripUseCase.TripPayload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users/{nickname}/trips")
public class TripController {

    private final TripUseCase tripUseCase;

    public TripController(TripUseCase tripUseCase) {
        this.tripUseCase = tripUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TripPayload newTrip(@PathVariable String nickname, @RequestBody TripPayload tripPayload) throws NoCarAvailableForTrip, InvalidSuppliedDataException, UserHasOpenTrip {
        return tripUseCase.newTrip(nickname, tripPayload);
    }
}
