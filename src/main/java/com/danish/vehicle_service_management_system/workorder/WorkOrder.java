package com.danish.vehicle_service_management_system.workorder;


import com.danish.vehicle_service_management_system.mechanic.Mechanic;
import com.danish.vehicle_service_management_system.serviceitem.ServiceItem;
import com.danish.vehicle_service_management_system.vehicle.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "work_order")
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    @Column(nullable = false)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime completedAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonIgnore
    private Vehicle vehicle;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_id", nullable = true)
    @JsonIgnore
    private Mechanic mechanic;


    @OneToMany(mappedBy = "workOrder")
    @JsonIgnore
    private List<ServiceItem> serviceItemList;



    @PrePersist
    void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public WorkOrder() {
    }

    public WorkOrder(Long id, WorkOrderStatus status, String notes, LocalDateTime createdAt, LocalDateTime completedAt, Vehicle vehicle, Mechanic mechanic, List<ServiceItem> serviceItemList) {
        this.id = id;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.vehicle = vehicle;
        this.mechanic = mechanic;
        this.serviceItemList = serviceItemList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOrderStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public List<ServiceItem> getServiceItemList() {
        return serviceItemList;
    }

    public void setServiceItemList(List<ServiceItem> serviceItemList) {
        this.serviceItemList = serviceItemList;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrder workOrder = (WorkOrder) o;
        return Objects.equals(id, workOrder.id) && Objects.equals(status, workOrder.status) && Objects.equals(notes, workOrder.notes) && Objects.equals(createdAt, workOrder.createdAt) && Objects.equals(completedAt, workOrder.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, notes, createdAt, completedAt);
    }
}
