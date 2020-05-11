package org.fiap.homework.fiap.rebu.vehicle.application;

import org.fiap.homework.fiap.rebu.vehicle.domain.usecase.VehicleUseCase;
import org.fiap.homework.fiap.rebu.vehicle.domain.usecase.VehicleUseCase.VehiclePayload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
