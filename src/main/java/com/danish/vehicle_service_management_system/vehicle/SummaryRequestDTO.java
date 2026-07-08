package com.danish.vehicle_service_management_system.vehicle;

public class SummaryRequestDTO {

    private Long VehicleId;
    private String text;
    private Integer resultLength;


    public Integer getResultLength() {
        return resultLength;
    }

    public void setResultLength(Integer resultLength) {
        this.resultLength = resultLength;
    }

    public SummaryRequestDTO() {
    }

    public Long getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        VehicleId = vehicleId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
