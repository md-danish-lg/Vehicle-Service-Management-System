package com.danish.vehicle_service_management_system.workorder;


import com.danish.vehicle_service_management_system.vehicle.Vehicle;
import com.danish.vehicle_service_management_system.vehicle.VehicleNotFoundException;
import com.danish.vehicle_service_management_system.vehicle.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderService {
    private final VehicleRepository vehicleRepository;
    private final WorkOrderRepository workOrderRepository;

    public WorkOrderService(VehicleRepository vehicleRepository, WorkOrderRepository workOrderRepository) {
        this.vehicleRepository = vehicleRepository;
        this.workOrderRepository = workOrderRepository;
    }

    public void addNewWorkOrder(@Valid WorkOrderRequestDTO dto) {
        Long vehicleId = dto.getVehicleId();
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(()->
                new VehicleNotFoundException(vehicleId));

        WorkOrder workOrder = new WorkOrder();
        workOrder.setStatus(WorkOrderStatus.CREATED);
        workOrder.setVehicle(vehicle);



        workOrder.setNotes(dto.getNotes());

        workOrderRepository.save(workOrder);


    }
}
