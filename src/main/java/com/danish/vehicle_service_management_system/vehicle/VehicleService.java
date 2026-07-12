package com.danish.vehicle_service_management_system.vehicle;


import com.danish.vehicle_service_management_system.customer.Customer;
import com.danish.vehicle_service_management_system.customer.CustomerNotFoundException;
import com.danish.vehicle_service_management_system.customer.CustomerRepository;
import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import com.danish.vehicle_service_management_system.workorder.WorkOrderRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final WorkOrderRepository workOrderRepository;

    public VehicleService(VehicleRepository vehicleRepository, CustomerRepository customerRepository, WorkOrderRepository workOrderRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.workOrderRepository = workOrderRepository;
    }

    public void addVehicle(VehicleRequestDTO dto) {

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(()->
                        new CustomerNotFoundException(dto.getCustomerId()));

        Vehicle vehicle = new Vehicle();

        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setCustomer(customer);


        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setMileage(dto.getMileage());

        vehicleRepository.save(vehicle);

    }

    public Vehicle findVehicleById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(()->new VehicleNotFoundException(id));
    }
    @Cacheable(value="vehicleHistory", key = "#id")
    public List<WorkOrder> getWorkOrdersById(Long id) {
        return workOrderRepository.findWorkOrdersByVehicle_Id(id);
    }

}
