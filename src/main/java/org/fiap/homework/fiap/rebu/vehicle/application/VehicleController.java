package org.fiap.homework.fiap.rebu.vehicle.application;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.vehicle.domain.exception.VehicleNotFoundException;
import org.fiap.homework.fiap.rebu.vehicle.domain.usecase.VehicleUseCase;
import org.fiap.homework.fiap.rebu.vehicle.domain.usecase.VehicleUseCase.VehiclePayload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehicles")
public class VehicleController {

    private final VehicleUseCase vehicleUseCase;

    public VehicleController(VehicleUseCase vehicleUseCase) {
        this.vehicleUseCase = vehicleUseCase;
    }

    @GetMapping
    public List<VehiclePayload> listVehicles() {
        return vehicleUseCase.listVehicles();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVehicle(@RequestBody VehiclePayload vehiclePayload) throws InvalidSuppliedDataException {
        this.vehicleUseCase.saveVehicle(vehiclePayload);
    }

    @PatchMapping
    @RequestMapping("{plate}/repair/do")
    public void putVehicleOnRepairing(@PathVariable String plate) throws VehicleNotFoundException {
        this.vehicleUseCase.putVehicleOnRepairing(
                new VehiclePayload(
                        plate
                )
        );
    }

    @PatchMapping
    @RequestMapping("{plate}/repair/done")
    public void concludeRepairingForVehicle(@PathVariable String plate) throws VehicleNotFoundException {
        this.vehicleUseCase.concludeRepairingForVehicle(
                new VehiclePayload(
                        plate
                )
        );
    }
}
