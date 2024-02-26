package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.service.CustomerService;
import com.enigma.tokonyadia.utils.constant.ApiUrlConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrlConstant.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomer() {
        return customerService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody String id) {
        return customerService.update(id);

    }

    @DeleteMapping("{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
    }

}
