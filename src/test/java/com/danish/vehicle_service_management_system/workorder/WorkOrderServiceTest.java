package com.danish.vehicle_service_management_system.workorder;

import com.danish.vehicle_service_management_system.mechanic.Mechanic;
import com.danish.vehicle_service_management_system.mechanic.MechanicRepository;
import com.danish.vehicle_service_management_system.serviceitem.NotEnoughItemsException;
import com.danish.vehicle_service_management_system.serviceitem.ServiceItem;
import com.danish.vehicle_service_management_system.serviceitem.ServiceItemRepository;
import com.danish.vehicle_service_management_system.vehicle.AiService;
import com.danish.vehicle_service_management_system.vehicle.RepairRecordDTO;
import com.danish.vehicle_service_management_system.vehicle.Vehicle;
import com.danish.vehicle_service_management_system.vehicle.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class WorkOrderServiceTest {


    @Mock
    private WorkOrderRepository workOrderRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private MechanicRepository mechanicRepository;
    @Mock
    private ServiceItemRepository serviceItemRepository;
    @Mock
    private AiService aiService;

    private WorkOrderService underTest;

    @BeforeEach
    void setUp(){
        underTest = new WorkOrderService(vehicleRepository, workOrderRepository, mechanicRepository, serviceItemRepository, aiService);
    }



    @Test
    void throwsWhenStatusNotCreated(){
        Long id = 1L;
        WorkOrder workOrder = new WorkOrder();
        Mechanic mechanic = new Mechanic();
        WorkOrderPatchDTO dto = new WorkOrderPatchDTO();
        dto.setMechanicId(id);

        when(workOrderRepository.findById(id)).thenReturn(Optional.of(workOrder));
        when(mechanicRepository.findById(id)).thenReturn(Optional.of(mechanic));

        workOrder.setStatus(WorkOrderStatus.ASSIGNED);

        assertThatThrownBy(()->underTest.assignMechanic(id, dto))
                .isInstanceOf(InvalidWorkOrderStateException.class)
                .hasMessageContaining("Invalid Transition, Cannot Assign Already Assigned Work Order");
    }


    @Test
    void throwsWhenStatusNotAssigned(){
        Long id = 1L;
        WorkOrder workOrder = new WorkOrder();


        when(workOrderRepository.findById(id)).thenReturn(Optional.of(workOrder));

        workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);

        assertThatThrownBy(()->underTest.startWorkOrder(id))
                .isInstanceOf(InvalidWorkOrderStateException.class)
                .hasMessageContaining("Invalid Transition, Must be assigned then moved to in Progress");

    }


    @Test
    void throwsWhenStatusNotInProgress(){
        Long id = 1L;
        WorkOrder workOrder = new WorkOrder();

        when(workOrderRepository.findById(id)).thenReturn(Optional.of(workOrder));

        workOrder.setStatus(WorkOrderStatus.CREATED);

        assertThatThrownBy(()->underTest.completeWorkOrder(id))
                .isInstanceOf(InvalidWorkOrderStateException.class)
                .hasMessageContaining("Invalid Transition, Can only Complete an In Progress Order");


    }

    @Test
    void throwsWhenNoServiceItems(){
        Long id = 1L;
        WorkOrder workOrder = new WorkOrder();

        when(workOrderRepository.findById(id)).thenReturn(Optional.of(workOrder));

        workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        when(serviceItemRepository.getServiceItemsByWorkOrder(workOrder)).thenReturn(List.of());


        assertThatThrownBy(()->underTest.completeWorkOrder(id))
                .isInstanceOf(NotEnoughItemsException.class);

    }

    @Test
    void assignsMechanicCorrectly(){
        Long id = 1L;
        WorkOrder workOrder = new WorkOrder();
        Mechanic mechanic = new Mechanic();
        WorkOrderPatchDTO dto = new WorkOrderPatchDTO();
        dto.setMechanicId(id);

        when(workOrderRepository.findById(id)).thenReturn(Optional.of(workOrder));
        when(mechanicRepository.findById(id)).thenReturn(Optional.of(mechanic));

        workOrder.setStatus(WorkOrderStatus.CREATED);

        underTest.assignMechanic(id, dto);
        verify(workOrderRepository).save(workOrder);

    }

    @Test
    void startOrderCorrectly(){
        Long id = 1L;
        WorkOrder workOrder = new WorkOrder();

        when(workOrderRepository.findById(id)).thenReturn(Optional.of(workOrder));

        workOrder.setStatus(WorkOrderStatus.ASSIGNED);
        underTest.startWorkOrder(id);

        verify(workOrderRepository).save(workOrder);


    }


    @Test
    void completeWorkOrderCorrectly(){
        Long id = 1L;
        WorkOrder workOrder = new WorkOrder();
        List<ServiceItem> serviceItems = List.of(
                new ServiceItem(),
                new ServiceItem()
        );


        when(workOrderRepository.findById(id)).thenReturn(Optional.of(workOrder));

        workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        when(serviceItemRepository.getServiceItemsByWorkOrder(workOrder)).thenReturn(serviceItems);

        workOrder.setVehicle(new Vehicle());

        underTest.completeWorkOrder(id);


        verify(aiService).addRepairRecord(any(RepairRecordDTO.class));
        verify(workOrderRepository).save(workOrder);



    }
}