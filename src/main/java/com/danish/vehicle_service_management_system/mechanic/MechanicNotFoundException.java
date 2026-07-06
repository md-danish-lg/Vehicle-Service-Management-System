package com.danish.vehicle_service_management_system.mechanic;

public class MechanicNotFoundException extends RuntimeException {
    public MechanicNotFoundException(Long id) {

        super("Mechanic with id: "+ id + " Not Found!");
    }
}
