package com.danish.vehicle_service_management_system.serviceitem;


import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "service_item")
public class ServiceItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float laborCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    @JsonBackReference
    private WorkOrder workOrder;


    public ServiceItem() {
    }

    public ServiceItem(Long id, String description, Float laborCost, WorkOrder workOrder) {
        this.id = id;
        this.description = description;
        this.laborCost = laborCost;
        this.workOrder = workOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServiceItem that = (ServiceItem) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(laborCost, that.laborCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, laborCost);
    }
}
