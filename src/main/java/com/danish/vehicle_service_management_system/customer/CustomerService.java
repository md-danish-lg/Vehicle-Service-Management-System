package com.danish.vehicle_service_management_system.customer;


import org.springframework.stereotype.Service;

import java.util.List;

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
}
