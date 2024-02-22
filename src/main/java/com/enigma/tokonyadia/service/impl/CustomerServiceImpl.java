package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.repository.CustomerRepository;
import com.enigma.tokonyadia.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) return optionalCustomer.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");

    }

    @Override
    public Customer update(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
        return create(customer);
    }

    @Override
    public void deleteById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
        Customer customer = optionalCustomer.get();
        customerRepository.delete(customer);
    }
}
