package com.danish.vehicle_service_management_system;


import com.danish.vehicle_service_management_system.customer.CustomerNotFoundException;
import com.danish.vehicle_service_management_system.mechanic.MechanicNotFoundException;
import com.danish.vehicle_service_management_system.serviceitem.NotEnoughItemsException;
import com.danish.vehicle_service_management_system.workorder.InvalidWorkOrderStateException;
import com.danish.vehicle_service_management_system.workorder.WorkOrderNotFoundException;
import com.danish.vehicle_service_management_system.vehicle.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<String> handleVehicleNotFound(VehicleNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(WorkOrderNotFoundException.class)
    public ResponseEntity<String> handleWorkOrderNotFound(WorkOrderNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MechanicNotFoundException.class)
    public ResponseEntity<String> handleMechanicNotFound(MechanicNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidWorkOrderStateException.class)
    public ResponseEntity<String> handleInvalidStateException(InvalidWorkOrderStateException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(NotEnoughItemsException.class)
    public ResponseEntity<String> handleNotEnoughItems(NotEnoughItemsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }



}
