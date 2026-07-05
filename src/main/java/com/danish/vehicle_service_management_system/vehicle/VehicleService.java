package com.danish.vehicle_service_management_system.vehicle;


import com.danish.vehicle_service_management_system.customer.Customer;
import com.danish.vehicle_service_management_system.customer.CustomerNotFoundException;
import com.danish.vehicle_service_management_system.customer.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    public VehicleService(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
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
}
