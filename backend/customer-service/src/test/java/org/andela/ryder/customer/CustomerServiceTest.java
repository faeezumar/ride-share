package org.andela.ryder.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    public void testRegisterCustomer() {
        // Create a RegisterRequest object for testing
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("TestName");
        registerRequest.setEmail("test@example.com");
        // Set other properties as needed

        // Create a CustomerEntity that would be returned by the repository
        CustomerEntity savedInstance = new CustomerEntity();
        savedInstance.setId(1L);
        savedInstance.setName(registerRequest.getName());
        savedInstance.setEmail(registerRequest.getEmail());
        // Set other properties as needed

        // Mock the repository's save method to return the saved entity
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedInstance);

        // Call the service method
        CustomerDTO result = customerService.registerCustomer(registerRequest);

        // Verify the repository save method was called
        verify(customerRepository).save(any(CustomerEntity.class));

        // Add assertions to verify the result
        // For example:
        assertEquals(savedInstance.getId(), result.getId());
        assertEquals(savedInstance.getName(), result.getName());
        assertEquals(savedInstance.getEmail(), result.getEmail());
        // Add more assertions as needed
    }

    @Test
    public void testGetCustomer() {
        // Replace customerId with a valid customer ID
        Long customerId = 1L;

        // Create a CustomerEntity that would be returned by the repository
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerId);
        customerEntity.setName("TestName");
        customerEntity.setEmail("test@example.com");
        // Set other properties as needed

        // Mock the repository's findById method to return the entity
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));

        // Call the service method
        CustomerDTO result = customerService.getCustomer(customerId);

        // Verify the repository findById method was called
        verify(customerRepository).findById(customerId);

        // Add assertions to verify the result
        // For example:
        assertEquals(customerEntity.getId(), result.getId());
        assertEquals(customerEntity.getName(), result.getName());
        assertEquals(customerEntity.getEmail(), result.getEmail());
        // Add more assertions as needed
    }
}