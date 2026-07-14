package com.danish.vehicle_service_management_system.workorder;


import com.danish.vehicle_service_management_system.mechanic.Mechanic;
import com.danish.vehicle_service_management_system.mechanic.MechanicNotFoundException;
import com.danish.vehicle_service_management_system.mechanic.MechanicRepository;
import com.danish.vehicle_service_management_system.serviceitem.NotEnoughItemsException;
import com.danish.vehicle_service_management_system.serviceitem.ServiceItem;
import com.danish.vehicle_service_management_system.serviceitem.ServiceItemRepository;
import com.danish.vehicle_service_management_system.vehicle.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkOrderService {
    private final VehicleRepository vehicleRepository;
    private final WorkOrderRepository workOrderRepository;
    private final MechanicRepository mechanicRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final AiService aiService;

    public WorkOrderService(VehicleRepository vehicleRepository, WorkOrderRepository workOrderRepository, MechanicRepository mechanicRepository, ServiceItemRepository serviceItemRepository, AiService aiService) {
        this.vehicleRepository = vehicleRepository;
        this.workOrderRepository = workOrderRepository;
        this.mechanicRepository = mechanicRepository;
        this.serviceItemRepository = serviceItemRepository;
        this.aiService = aiService;
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

    public WorkOrderResponseDTO getWorkOrderWithServiceItems(Long id) {
        WorkOrder workOrder = workOrderRepository.findWithServiceItemsById(id)
                .orElseThrow(() -> new WorkOrderNotFoundException(id));
        return mapToDTO(workOrder);
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

    @Transactional
    public void completeWorkOrder(Long id) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElseThrow(()->
                new WorkOrderNotFoundException(id));

        List<ServiceItem> serviceItems = serviceItemRepository.getServiceItemsByWorkOrder(workOrder);




        if(workOrder.getStatus() == WorkOrderStatus.IN_PROGRESS){
            if (!(serviceItems.isEmpty())){
                workOrder.setStatus(WorkOrderStatus.COMPLETED);
                workOrder.setCompletedAt(LocalDateTime.now());

                Vehicle vehicle = workOrder.getVehicle();
                String text = vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getYear() + " - " +
                        serviceItems.stream().map(ServiceItem::getDescription)
                                .collect(Collectors.joining(", "));

                RepairRecordDTO dto = new RepairRecordDTO();

                dto.setText(text);
                dto.setId(String.valueOf(workOrder.getId()));
                dto.setVehicleId(vehicle.getId());

                aiService.addRepairRecord(dto);


            }else{
                throw new NotEnoughItemsException();
            }
        }else{
            throw new InvalidWorkOrderStateException("Invalid Transition, Can only Complete an In Progress Order");
        }

        workOrderRepository.save(workOrder);
    }


    private WorkOrderResponseDTO mapToDTO(WorkOrder workOrder) {
        WorkOrderResponseDTO dto = new WorkOrderResponseDTO();
        dto.setId(workOrder.getId());
        dto.setStatus(workOrder.getStatus());
        dto.setNotes(workOrder.getNotes());
        dto.setCreatedAt(workOrder.getCreatedAt());
        dto.setCompletedAt(workOrder.getCompletedAt());
        // map vehicle fields
        dto.setVehicleId(workOrder.getVehicle().getId());
        dto.setVehicleMake(workOrder.getVehicle().getMake());
        dto.setVehicleModel(workOrder.getVehicle().getModel());
        dto.setVehicleYear(workOrder.getVehicle().getYear());
        dto.setLicensePlate(workOrder.getVehicle().getLicensePlate());

        dto.setCustomerId(workOrder.getVehicle().getCustomer().getId());
        dto.setCustomerName(workOrder.getVehicle().getCustomer().getName());

        if (workOrder.getMechanic() != null) {
            dto.setMechanicId(workOrder.getMechanic().getId());
            dto.setMechanicName(workOrder.getMechanic().getName());
        }

        dto.setServiceItemList(workOrder.getServiceItemList());

        return dto;
    }
}
