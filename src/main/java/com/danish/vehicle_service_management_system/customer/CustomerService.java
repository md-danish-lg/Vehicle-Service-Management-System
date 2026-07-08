package com.danish.vehicle_service_management_system.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public CustomerWithVehiclesDTO FindCustomerWithVehicles(Long id) {
        Customer customer =  customerRepository.findWithVehiclesById(id).orElseThrow(() ->
                new CustomerNotFoundException(id));

        return new CustomerWithVehiclesDTO(customer);
    }
}
