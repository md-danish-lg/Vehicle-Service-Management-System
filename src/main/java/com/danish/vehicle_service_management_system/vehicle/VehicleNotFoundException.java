package com.danish.vehicle_service_management_system.vehicle;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super("Vehicle with id: " + id + " not Found!");
    }
}
