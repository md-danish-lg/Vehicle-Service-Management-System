package com.danish.vehicle_service_management_system.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class VehicleRequestDTO {

    @NotBlank(message="Vehicle make is required!")
    private String make;

    @NotBlank(message = "Vehicle model is required!")
    private String model;

    @NotNull(message = "Vehicle Year cannot be empty")
    private Integer year;

    @NotNull(message="Customer id is required")
    private Long customerId;


    private String licensePlate;
    private Integer mileage;


    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
