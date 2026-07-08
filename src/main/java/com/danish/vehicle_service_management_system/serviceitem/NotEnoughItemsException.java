package com.danish.vehicle_service_management_system.serviceitem;

public class NotEnoughItemsException extends RuntimeException {
    public NotEnoughItemsException() {
        super("There must be at least one Service item");
    }
}
