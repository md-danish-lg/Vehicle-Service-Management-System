package com.danish.vehicle_service_management_system.mechanic;

import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }


    @GetMapping
    public List<Mechanic> getAllMechanics(){
        return mechanicService.getMechanics();
    }

    @PostMapping
    public ResponseEntity<String> addMechanic(@RequestBody Mechanic mechanic){
        mechanicService.saveMechanic(mechanic);
        return ResponseEntity.ok("Mechanic Saved Successfully!");
    }

    @GetMapping("{id}/workorders")
    public List<WorkOrder> getAllWorkOrdersById(@PathVariable Long id){
        return mechanicService.getWorkOrdersByMechanicId(id);
    }

}
