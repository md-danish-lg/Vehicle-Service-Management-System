package com.danish.vehicle_service_management_system.workorder;


import com.danish.vehicle_service_management_system.mechanic.Mechanic;
import com.danish.vehicle_service_management_system.mechanic.MechanicNotFoundException;
import com.danish.vehicle_service_management_system.mechanic.MechanicRepository;
import com.danish.vehicle_service_management_system.serviceitem.NotEnoughItemsException;
import com.danish.vehicle_service_management_system.serviceitem.ServiceItem;
import com.danish.vehicle_service_management_system.serviceitem.ServiceItemRepository;
import com.danish.vehicle_service_management_system.vehicle.Vehicle;
import com.danish.vehicle_service_management_system.vehicle.VehicleNotFoundException;
import com.danish.vehicle_service_management_system.vehicle.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkOrderService {
    private final VehicleRepository vehicleRepository;
    private final WorkOrderRepository workOrderRepository;
    private final MechanicRepository mechanicRepository;
    private final ServiceItemRepository serviceItemRepository;

    public WorkOrderService(VehicleRepository vehicleRepository, WorkOrderRepository workOrderRepository, MechanicRepository mechanicRepository, ServiceItemRepository serviceItemRepository) {
        this.vehicleRepository = vehicleRepository;
        this.workOrderRepository = workOrderRepository;
        this.mechanicRepository = mechanicRepository;
        this.serviceItemRepository = serviceItemRepository;
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

    public WorkOrder getWorkOrderWithServiceItems(Long id) {
        return workOrderRepository.findWithServiceItemsById(id).orElseThrow(()->
                new WorkOrderNotFoundException(id));
    }

    public void assignMechanic(Long id, @Valid WorkOrderPatchDTO dto) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElseThrow(()->
                new WorkOrderNotFoundException(id));

        Mechanic mechanic = mechanicRepository.findById(dto.getMechanicId()).orElseThrow(()->
                new MechanicNotFoundException(dto.getMechanicId()));


        if(workOrder.getStatus() == WorkOrderStatus.CREATED){

            workOrder.setStatus(WorkOrderStatus.ASSIGNED);
            workOrder.setMechanic(mechanic);
        }else{
            throw new InvalidWorkOrderStateException("Invalid Transition, Cannot Assign Already Assigned Work Order");
        }

        workOrderRepository.save(workOrder);
    }

    public void startWorkOrder(Long id) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElseThrow(()->
                new WorkOrderNotFoundException(id));

        if(workOrder.getStatus() == WorkOrderStatus.ASSIGNED){
            workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        }else{
            throw new InvalidWorkOrderStateException("Invalid Transition, Must be assigned then moved to in Progress");
        }

        workOrderRepository.save(workOrder);


    }

    public void completeWorkOrder(Long id) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElseThrow(()->
                new WorkOrderNotFoundException(id));

        List<ServiceItem> serviceItems = serviceItemRepository.getServiceItemsByWorkOrder(workOrder);


        if(workOrder.getStatus() == WorkOrderStatus.IN_PROGRESS){
            if (!(serviceItems.isEmpty())){
                workOrder.setStatus(WorkOrderStatus.COMPLETED);
                workOrder.setCompletedAt(LocalDateTime.now());
            }else{
                throw new NotEnoughItemsException();
            }
        }else{
            throw new InvalidWorkOrderStateException("Invalid Transition, Can only Complete an In Progress Order");
        }

        workOrderRepository.save(workOrder);
    }
}
