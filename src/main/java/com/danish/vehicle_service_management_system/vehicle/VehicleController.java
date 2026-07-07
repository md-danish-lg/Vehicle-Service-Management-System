package com.danish.vehicle_service_management_system.vehicle;

import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<String> addNewVehicle(@Valid @RequestBody VehicleRequestDTO dto){
        vehicleService.addVehicle(dto);
        return ResponseEntity.ok("Vehicle added Successfully");
    }

    @GetMapping("{id}")
    public Vehicle getVehicleById(@PathVariable Long id){
        return vehicleService.findVehicleById(id);

    }

    @GetMapping("{id}/history")
    public List<WorkOrder> getAllWorkOrders(@PathVariable Long id){
        return vehicleService.getWorkOrdersById(id);
    }

}
