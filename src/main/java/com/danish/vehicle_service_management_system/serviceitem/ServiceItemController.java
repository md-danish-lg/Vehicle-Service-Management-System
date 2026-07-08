package com.danish.vehicle_service_management_system.serviceitem;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/workorders")
public class ServiceItemController {


    private final ServiceItemService serviceItemService;

    public ServiceItemController(ServiceItemService serviceItemService) {
        this.serviceItemService = serviceItemService;
    }


    @GetMapping("{id}/services")
    public List<ServiceItem> getAllServiceItems(@PathVariable Long id){
        return serviceItemService.getAllServiceItemsByWorkOrder(id);
    }
    @PostMapping("{id}/services")
    public ResponseEntity<String> addServiceItem(@PathVariable Long id, @Valid @RequestBody ServiceItemRequestDTO dto){
        serviceItemService.addNewServiceItem(id, dto);
        return ResponseEntity.ok("Service item added successfully");

    }

}
