package com.danish.vehicle_service_management_system.mechanic;


import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;


    public MechanicService(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;
    }


    public List<Mechanic> getMechanics() {
        return mechanicRepository.findAll();
    }

    public void saveMechanic(Mechanic mechanic) {
        mechanicRepository.save(mechanic);
    }

    public List<WorkOrder> getWorkOrdersByMechanicId(Long id) {
        if(!(mechanicRepository.existsById(id))){
            throw new MechanicNotFoundException(id);
        }

    }
}

