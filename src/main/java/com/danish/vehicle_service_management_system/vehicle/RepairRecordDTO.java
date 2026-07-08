package com.danish.vehicle_service_management_system.vehicle;

public class RepairRecordDTO {

    private String id;
    private String text;
    private Long VehicleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.VehicleId= vehicleId;
    }
}
