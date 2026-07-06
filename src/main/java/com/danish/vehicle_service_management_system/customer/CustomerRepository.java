package com.danish.vehicle_service_management_system.customer;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @EntityGraph(attributePaths = {"vehicle"})
    Optional<Customer> findWithVehiclesById(Long id);
}
