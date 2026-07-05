package com.danish.vehicle_service_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VehicleServiceManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleServiceManagementSystemApplication.class, args);
    }


    @GetMapping("/")
    public String CheckStatus(){
        return "STATUS: WORKING";
    }



}
