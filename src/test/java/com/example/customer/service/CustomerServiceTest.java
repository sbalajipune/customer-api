package com.example.customer.service;

import com.example.customer.domain.Customer;
import com.example.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    private final CustomerRepository repository = mock(CustomerRepository.class);
    private final CustomerService service = new CustomerService(repository);

    @Test
    void createDelegatesToRepository() {
        Customer customer = new Customer("Ada Lovelace", "ada@example.com");
        when(repository.save(customer)).thenReturn(customer);

        Customer result = service.create(customer);

        assertSame(customer, result);
        verify(repository).save(customer);
    }

    @Test
    void findAllReturnsRepositoryResults() {
        List<Customer> customers = List.of(
                new Customer("Ada Lovelace", "ada@example.com"),
                new Customer("Grace Hopper", "grace@example.com"));
        when(repository.findAll()).thenReturn(customers);

        List<Customer> result = service.findAll();

        assertEquals(customers, result);
        verify(repository).findAll();
    }

    @Test
    void findByIdReturnsCustomerWhenPresent() {
        Customer customer = new Customer("Ada Lovelace", "ada@example.com");
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = service.findById(1L);

        assertSame(customer, result);
        verify(repository).findById(1L);
    }

    @Test
    void findByIdThrowsWhenCustomerIsMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.findById(99L));

        assertEquals("Customer not found: 99", exception.getMessage());
        verify(repository).findById(99L);
    }
}