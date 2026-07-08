package com.danish.vehicle_service_management_system.serviceitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiceItemRequestDTO {


    @NotBlank(message = "description cannot be empty")
    private String description;

    @NotNull(message = "Labor cost cannot be empty")
    private Float laborCost;




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Float laborCost) {
        this.laborCost = laborCost;
    }


}
