package com.danish.vehicle_service_management_system.workorder;

import jakarta.validation.constraints.NotNull;

public class WorkOrderPatchDTO {

    @NotNull(message = "Mechanic Id cannot be empty!")
    private Long mechanicId;

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }
}
