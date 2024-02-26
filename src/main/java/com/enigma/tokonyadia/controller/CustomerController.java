package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.dto.request.CustomerDto;
import com.enigma.tokonyadia.dto.request.ProductDto;
import com.enigma.tokonyadia.dto.response.PageResponseWrapper;
import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.service.CustomerService;
import com.enigma.tokonyadia.utils.constant.ApiUrlConstant;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> createNewCustomer(@RequestBody CustomerDto customer) {
        return customerService.create(customer);
    }

    @GetMapping
    public ResponseEntity<?> getPerPage(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                                        @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
                                        @RequestParam(name = "direction", defaultValue = "ASC") String direction,
                                        @ModelAttribute CustomerDto customerDto){
        return customerService.getCustomerPerPage(page, size, sortBy, direction, customerDto);
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody CustomerDto customerDto) {
        return customerService.update(id, customerDto);

    }

    @DeleteMapping("{id}")
    public void deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
    }

}
