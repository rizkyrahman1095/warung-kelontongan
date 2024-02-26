package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.dto.request.CustomerDto;
import com.enigma.tokonyadia.dto.request.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> create(CustomerDto customerDto);
    ResponseEntity<?> getById(String id);
    ResponseEntity<?> update(String id, CustomerDto customerDto);
    ResponseEntity<?> deleteById(String id);
    ResponseEntity<?> getCustomerPerPage(Integer page, Integer size, String sortBy, String direction, CustomerDto customerDto);

}
