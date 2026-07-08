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
    public WorkOrder getFullWorkOrder(@PathVariable Long id){
        return workOrderService.getWorkOrderWithServiceItems(id);

    }
}
