package com.stefan.customer;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public CustomerDto getCustomer(@PathVariable Integer customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }

    @PutMapping("/{customerId}")
    public CustomerDto updateCustomer(@PathVariable Integer customerId, @Valid @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(customerId, customerDto);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
    }
}
