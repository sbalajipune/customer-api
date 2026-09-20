package com.example.customer.controller;

import com.example.customer.domain.Customer;
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
    void healthReturnsRunningMessage() {
        assertEquals("Customer API is running!", controller.health());
    }

    @Test
    void createDelegatesToService() {
        Customer customer = new Customer("Ada Lovelace", "ada@example.com");
        when(service.create(customer)).thenReturn(customer);

        Customer result = controller.create(customer);

        assertSame(customer, result);
        verify(service).create(customer);
    }

    @Test
    void findAllDelegatesToService() {
        List<Customer> customers = List.of(new Customer("Ada Lovelace", "ada@example.com"));
        when(service.findAll()).thenReturn(customers);

        List<Customer> result = controller.findAll();

        assertEquals(customers, result);
        verify(service).findAll();
    }

    @Test
    void findByIdDelegatesToService() {
        Customer customer = new Customer("Ada Lovelace", "ada@example.com");
        when(service.findById(1L)).thenReturn(customer);

        Customer result = controller.findById(1L);

        assertSame(customer, result);
        verify(service).findById(1L);
    }
}