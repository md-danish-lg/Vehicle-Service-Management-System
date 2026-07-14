package com.danish.vehicle_service_management_system.workorder;

import com.danish.vehicle_service_management_system.serviceitem.ServiceItem;

import java.time.LocalDateTime;
import java.util.List;

public class WorkOrderResponseDTO {
    private Long id;
    private WorkOrderStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    // vehicle info
    private Long vehicleId;
    private String vehicleMake;
    private String vehicleModel;
    private Integer vehicleYear;
    private String licensePlate;

    // customer info
    private Long customerId;
    private String customerName;

    // mechanic info
    private Long mechanicId;
    private String mechanicName;

    // service items
    private List<ServiceItem> serviceItemList;

    public WorkOrderResponseDTO() {
    }

    public WorkOrderResponseDTO(Long id, WorkOrderStatus status, String notes, LocalDateTime createdAt, LocalDateTime completedAt, Long vehicleId, String vehicleMake, String vehicleModel, Integer vehicleYear, String licensePlate, Long customerId, String customerName, Long mechanicId, String mechanicName, List<ServiceItem> serviceItemList) {
        this.id = id;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.vehicleId = vehicleId;
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.licensePlate = licensePlate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.mechanicId = mechanicId;
        this.mechanicName = mechanicName;
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

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public Integer getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public List<ServiceItem> getServiceItemList() {
        return serviceItemList;
    }

    public void setServiceItemList(List<ServiceItem> serviceItemList) {
        this.serviceItemList = serviceItemList;
    }
}
