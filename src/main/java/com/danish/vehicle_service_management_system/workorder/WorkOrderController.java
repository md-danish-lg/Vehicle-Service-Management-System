package com.danish.vehicle_service_management_system.workorder;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/workorders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @PostMapping
    public ResponseEntity<String> addWorkOrder(@Valid @RequestBody WorkOrderRequestDTO dto){
        workOrderService.addNewWorkOrder(dto);
        return ResponseEntity.ok("Work Order Added Successfully");

    }


    @GetMapping("{id}")
    public WorkOrderResponseDTO getFullWorkOrder(@PathVariable Long id){
        return workOrderService.getWorkOrderWithServiceItems(id);

    }


    @PatchMapping("{id}/assign")
    public ResponseEntity<String> assignMechanicToWorkOrder(@PathVariable Long id, @Valid @RequestBody WorkOrderPatchDTO dto){
        workOrderService.assignMechanic(id, dto);
        return ResponseEntity.ok("Mechanic assigned Successfully!");




    }

    @PatchMapping("{id}/start")
    public ResponseEntity<String> startWorking(@PathVariable Long id){
        workOrderService.startWorkOrder(id);
        return ResponseEntity.ok("Work Order status moved to In Progress");
    }

    @PatchMapping("{id}/complete")
    public ResponseEntity<String> finishWorkOrder(@PathVariable Long id){
        workOrderService.completeWorkOrder(id);
        return ResponseEntity.ok("Work Order Completed!");
    }

}
