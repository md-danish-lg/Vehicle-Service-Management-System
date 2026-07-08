package com.danish.vehicle_service_management_system.workorder;

public class WorkOrderNotFoundException extends RuntimeException {
    public WorkOrderNotFoundException(Long id) {
        super("Work order with id: "+ id + " Not Found");
    }
}
