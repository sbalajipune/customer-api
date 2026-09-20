package com.example.customer.service;

import com.example.customer.domain.Customer;
import com.example.customer.dto.CreateCustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    private final CustomerRepository repository = mock(CustomerRepository.class);
    private final CustomerService service = new CustomerService(repository);

    @Test
    void createDelegatesToRepositoryAndMapsToResponse() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setName("Ada Lovelace");
        request.setEmail("ada@example.com");

        Customer customer = new Customer("Ada Lovelace", "ada@example.com");
        ReflectionTestUtils.setField(customer, "id", 1L);
        when(repository.save(org.mockito.ArgumentMatchers.any(Customer.class))).thenReturn(customer);

        CustomerResponse result = service.create(request);

        assertEquals(1L, result.getId());
        assertEquals("Ada Lovelace", result.getName());
        assertEquals("ada@example.com", result.getEmail());
        verify(repository).save(org.mockito.ArgumentMatchers.any(Customer.class));
    }

    @Test
    void findAllReturnsRepositoryResultsMappedToResponses() {
        Customer ada = new Customer("Ada Lovelace", "ada@example.com");
        Customer grace = new Customer("Grace Hopper", "grace@example.com");
        ReflectionTestUtils.setField(ada, "id", 1L);
        ReflectionTestUtils.setField(grace, "id", 2L);

        List<Customer> customers = List.of(ada, grace);
        when(repository.findAll()).thenReturn(customers);

        List<CustomerResponse> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Ada Lovelace", result.get(0).getName());
        assertEquals("ada@example.com", result.get(0).getEmail());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Grace Hopper", result.get(1).getName());
        assertEquals("grace@example.com", result.get(1).getEmail());
        verify(repository).findAll();
    }

    @Test
    void findByIdReturnsCustomerWhenPresent() {
        Customer customer = new Customer("Ada Lovelace", "ada@example.com");
        ReflectionTestUtils.setField(customer, "id", 1L);
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerResponse result = service.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Ada Lovelace", result.getName());
        assertEquals("ada@example.com", result.getEmail());
        verify(repository).findById(1L);
    }

    @Test
    void findByIdThrowsWhenCustomerIsMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> service.findById(99L));

        assertEquals("Customer not found: 99", exception.getMessage());
        verify(repository).findById(99L);
    }
}