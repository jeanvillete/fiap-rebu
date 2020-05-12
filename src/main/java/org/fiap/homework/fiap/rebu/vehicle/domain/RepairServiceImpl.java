package org.fiap.homework.fiap.rebu.vehicle.domain;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class RepairServiceImpl implements RepairService {

    private final RepairRepository repairRepository;

    RepairServiceImpl(RepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }


    @Override
    public void save(Repair repair) {
        repairRepository.save(repair);
    }

    @Override
    public Optional<Repair> getRepairByVehicle(Vehicle vehicle) {
        return repairRepository.findByVehicle(vehicle);
    }
}
