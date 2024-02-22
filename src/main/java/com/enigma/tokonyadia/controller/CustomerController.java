package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "/customers")
    public Customer createNewCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.create(customer);
        return newCustomer;
    }

    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomer() {
        List<Customer> customers = customerService.getAll();
        return customers;
    }

    @GetMapping(path = "/customers/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getById(id);
        return customer;
    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        Customer updatedCustomer = customerService.update(customer);
        return updatedCustomer;
    }

    @DeleteMapping(path = "/customers/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
    }

}
