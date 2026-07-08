package com.danish.vehicle_service_management_system.workorder;

import com.danish.vehicle_service_management_system.mechanic.Mechanic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findWorkOrdersByMechanicAndStatusNot(Mechanic mechanic, WorkOrderStatus status);

    List<WorkOrder> findWorkOrdersByVehicle_Id(Long id);


    @EntityGraph(attributePaths = {"serviceItemList"})
    Optional<WorkOrder> findWithServiceItemsById(Long id);
}
