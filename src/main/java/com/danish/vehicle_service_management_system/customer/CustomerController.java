package com.danish.vehicle_service_management_system.customer;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {


    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.findAllCustomers();
    }

    @PostMapping
    public ResponseEntity<String> addNewCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
        return ResponseEntity.ok("Customer Added Successfully");
    }

    @GetMapping("{id}")
    public CustomerWithVehiclesDTO getCustomerWithVehicles(@PathVariable Long id){
        return customerService.FindCustomerWithVehicles(id);
    }

}
