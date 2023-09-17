package org.andela.ryder.customer;

import org.andela.ryder.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    public CustomerDTO registerCustomer(RegisterRequest registerRequest) {
        var customerEntity = toEntity((registerRequest));
        //customerEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        var savedInstance =  customerRepository.save(customerEntity);
        return toDTO(savedInstance);
    }

    public CustomerDTO getCustomer(Long customerId) {
        var customerInstance = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(""));
        return toDTO(customerInstance);
    }

    private CustomerDTO toDTO(CustomerEntity savedInstance) {
        return CustomerDTO.builder()
                .name(savedInstance.getName())
                .email(savedInstance.getEmail())
                .address(savedInstance.getAddress())
                .id(savedInstance.getId())
                .build();
    }

    private CustomerEntity toEntity(CustomerDTO customerDTO) {
        return new CustomerEntity();
    }

    private CustomerEntity toEntity(RegisterRequest registerRequest) {
        return CustomerEntity.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .address(registerRequest.getAddress())
                .password(registerRequest.getPassword())
                .build();
    }
}