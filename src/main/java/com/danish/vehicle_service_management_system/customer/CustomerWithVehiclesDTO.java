package com.danish.vehicle_service_management_system.customer;

import com.danish.vehicle_service_management_system.vehicle.Vehicle;

import java.util.List;

public class CustomerWithVehiclesDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private List<Vehicle> vehicles;

    public CustomerWithVehiclesDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.phone = customer.getPhone();
        this.email = customer.getEmail();
        this.vehicles = customer.getVehicle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}