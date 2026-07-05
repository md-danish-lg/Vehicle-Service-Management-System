package com.danish.vehicle_service_management_system.serviceitem;


import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "service_item")
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float laborCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workOrder_id", nullable = false)
    @JsonIgnore
    private WorkOrder workOrder;

}
