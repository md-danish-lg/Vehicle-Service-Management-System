package com.danish.vehicle_service_management_system.mechanic;


import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import com.danish.vehicle_service_management_system.workorder.WorkOrderRepository;
import com.danish.vehicle_service_management_system.workorder.WorkOrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;
    private final WorkOrderRepository workOrderRepository;


    public MechanicService(MechanicRepository mechanicRepository, WorkOrderRepository workOrderRepository) {
        this.mechanicRepository = mechanicRepository;
        this.workOrderRepository = workOrderRepository;
    }


    public List<Mechanic> getMechanics() {
        return mechanicRepository.findAll();
    }

    public void saveMechanic(Mechanic mechanic) {
        mechanicRepository.save(mechanic);
    }

    public List<WorkOrder> getWorkOrdersByMechanicId(Long id) {

        Mechanic mechanic = mechanicRepository.findById(id).orElseThrow(
                ()->new MechanicNotFoundException(id));


        return workOrderRepository.findWorkOrdersByMechanicAndStatusNot(mechanic, WorkOrderStatus.COMPLETED);

    }
}

