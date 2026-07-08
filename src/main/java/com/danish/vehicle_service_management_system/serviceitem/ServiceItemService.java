package com.danish.vehicle_service_management_system.serviceitem;

import com.danish.vehicle_service_management_system.workorder.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceItemService {
    private final WorkOrderRepository workOrderRepository;
    private final ServiceItemRepository serviceItemRepository;

    public ServiceItemService(WorkOrderRepository workOrderRepository, ServiceItemRepository serviceItemRepository) {
        this.workOrderRepository = workOrderRepository;
        this.serviceItemRepository = serviceItemRepository;
    }

    public void addNewServiceItem(Long id, @Valid ServiceItemRequestDTO dto) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElseThrow(()->new WorkOrderNotFoundException(id));


        if(workOrder.getStatus() == WorkOrderStatus.COMPLETED){
            throw new InvalidWorkOrderStateException("Cannot add services to a completed work order");
        }

        ServiceItem serviceItem = new ServiceItem();

        serviceItem.setDescription(dto.getDescription());
        serviceItem.setLaborCost(dto.getLaborCost());
        serviceItem.setWorkOrder(workOrder);

        serviceItemRepository.save(serviceItem);
    }

    public List<ServiceItem> getAllServiceItemsByWorkOrder(Long id) {
        WorkOrder workOrder = workOrderRepository.findById(id).orElseThrow(()->
                new WorkOrderNotFoundException(id));

        return serviceItemRepository.getServiceItemsByWorkOrder(workOrder);
    }
}
