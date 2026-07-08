package com.danish.vehicle_service_management_system.workorder;

public class InvalidWorkOrderStateException extends RuntimeException {
    public InvalidWorkOrderStateException(String message) {
        super(message);
    }
}
