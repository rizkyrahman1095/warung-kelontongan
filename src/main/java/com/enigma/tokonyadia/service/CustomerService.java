package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.dto.request.CustomerDto;
import com.enigma.tokonyadia.entity.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<?> create(Customer customer);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(String id);
    ResponseEntity<?> update(String id);
    ResponseEntity<?> deleteById(String id);

}
