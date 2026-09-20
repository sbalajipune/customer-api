package com.example.customer.controller;

import com.example.customer.dto.CreateCustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.service.CustomerService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    private final CustomerService service = mock(CustomerService.class);
    private final CustomerController controller = new CustomerController(service);

    @Test
    void createDelegatesToService() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setName("Ada Lovelace");
        request.setEmail("ada@example.com");

        CustomerResponse expected = new CustomerResponse(1L, "Ada Lovelace", "ada@example.com");
        when(service.create(request)).thenReturn(expected);

        CustomerResponse result = controller.create(request);

        assertSame(expected, result);
        verify(service).create(request);
    }

    @Test
    void findAllDelegatesToService() {
        List<CustomerResponse> customers = List.of(
                new CustomerResponse(1L, "Ada Lovelace", "ada@example.com")
        );
        when(service.findAll()).thenReturn(customers);

        List<CustomerResponse> result = controller.findAll();

        assertEquals(customers, result);
        verify(service).findAll();
    }

    @Test
    void findByIdDelegatesToService() {
        CustomerResponse customer = new CustomerResponse(1L, "Ada Lovelace", "ada@example.com");
        when(service.findById(1L)).thenReturn(customer);

        CustomerResponse result = controller.findById(1L);

        assertSame(customer, result);
        verify(service).findById(1L);
    }
}