package com.danish.vehicle_service_management_system.vehicle;


import com.danish.vehicle_service_management_system.workorder.WorkOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate;
    @Value("${ai.service.url}")
    private String vehicleServiceUrl;


    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void addRepairRecord(RepairRecordDTO dto){

        Map<String, Object> request = new HashMap<>();
        request.put("id", dto.getId());
        request.put("vehicle_id", dto.getVehicleId());
        request.put("text", dto.getText());

        Map response = restTemplate.postForObject(
                vehicleServiceUrl + "/repair-history/add",
                request,
                Map.class
        );


    }
    public String summarize(Long id){

        Map<String, Object> request = new HashMap<>();
        request.put("vehicle_id", id);
        request.put("text", "full vehicle service history");
        request.put("result_length", 5);

        Map response = restTemplate.postForObject(
                vehicleServiceUrl + "/repair-history/summarize",
                request,
                Map.class
        );

        return (String) response.get("summary");
    }
}
