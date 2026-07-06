package com.danish.vehicle_service_management_system.workorder;


import jakarta.validation.constraints.NotNull;

public class WorkOrderRequestDTO {



    private String notes;

    @NotNull(message = "Vehicle id Cannot be empty!")
    private Long vehicleId;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
