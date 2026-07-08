package com.danish.vehicle_service_management_system.serviceitem;

import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {


    List<ServiceItem> getServiceItemsByWorkOrder(WorkOrder workOrder);
}
