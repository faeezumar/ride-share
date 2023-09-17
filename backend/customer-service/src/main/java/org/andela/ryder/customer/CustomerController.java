package org.andela.ryder.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody RegisterRequest registerRequest) {
        CustomerDTO registeredCustomer = customerService.registerCustomer(registerRequest);
        return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/one/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }
}
