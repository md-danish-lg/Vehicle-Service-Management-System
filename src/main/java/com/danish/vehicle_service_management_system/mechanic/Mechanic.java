package com.danish.vehicle_service_management_system.mechanic;


import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="mechanic")
public class Mechanic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;



    @OneToMany(mappedBy = "mechanic")
    @JsonIgnore
    private List<WorkOrder> workOrderList;


    public Mechanic(Long id, String name, String specialization, List<WorkOrder> workOrderList) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.workOrderList = workOrderList;
    }

    public Mechanic() {
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<WorkOrder> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<WorkOrder> workOrderList) {
        this.workOrderList = workOrderList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mechanic mechanic = (Mechanic) o;
        return Objects.equals(id, mechanic.id) && Objects.equals(name, mechanic.name) && Objects.equals(specialization, mechanic.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, specialization);
    }
}
