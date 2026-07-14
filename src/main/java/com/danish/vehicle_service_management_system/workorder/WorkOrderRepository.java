package com.danish.vehicle_service_management_system.workorder;

import com.danish.vehicle_service_management_system.mechanic.Mechanic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findWorkOrdersByMechanicAndStatusNot(Mechanic mechanic, WorkOrderStatus status);




    @EntityGraph(attributePaths = {"serviceItemList", "vehicle", "vehicle.customer", "mechanic"})
    Optional<WorkOrder> findWithServiceItemsById(Long id);

    @Query("SELECT w FROM WorkOrder w JOIN FETCH w.serviceItemList WHERE w.vehicle.id = :vehicleId")
    List<WorkOrder> findByVehicleId(@Param("vehicleId") Long vehicleId);
}
