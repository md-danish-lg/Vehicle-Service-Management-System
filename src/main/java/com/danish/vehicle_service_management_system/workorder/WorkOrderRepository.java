package com.danish.vehicle_service_management_system.workorder;

import com.danish.vehicle_service_management_system.mechanic.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findWorkOrdersByMechanicAndStatusNot(Mechanic mechanic, WorkOrderStatus status);

    List<WorkOrder> findWorkOrdersByVehicle_Id(Long id);
}
