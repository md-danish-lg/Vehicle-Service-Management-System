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
    private final AiService aiService;

    public VehicleController(VehicleService vehicleService, AiService aiService) {
        this.vehicleService = vehicleService;
        this.aiService = aiService;
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


    @GetMapping("{id}/summary")
    public ResponseEntity<String> summarizeWorkHistory(@PathVariable Long id){

        String summary = aiService.summarize(id);
        return ResponseEntity.ok(summary);
    }


}
