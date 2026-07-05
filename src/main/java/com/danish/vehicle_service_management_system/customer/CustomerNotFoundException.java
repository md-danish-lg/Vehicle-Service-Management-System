package com.danish.vehicle_service_management_system.customer;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Customer with id: "+ id + " Not found!");

    }
}
