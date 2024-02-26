package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.dto.request.CustomerDto;
import com.enigma.tokonyadia.dto.request.ProductDto;
import com.enigma.tokonyadia.dto.response.ControllerResponse;
import com.enigma.tokonyadia.dto.response.PageResponseWrapper;
import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.repository.CustomerRepository;
import com.enigma.tokonyadia.service.CustomerService;
import com.enigma.tokonyadia.utils.constant.ApiUrlConstant;
import com.enigma.tokonyadia.utils.spesification.CustomerSpesification;
import com.enigma.tokonyadia.utils.spesification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<?> create(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .name(customerDto.getName())
                .birthDate(customerDto.getBirthDate())
                .gender(customerDto.getGender())
                .address(customerDto.getAddress())
                .phoneNumber(customerDto.getPhoneNumber())
                .createDate(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .build();
        Customer saveCustomers = customerRepository.save(customer);
        ControllerResponse<CustomerDto> response = ControllerResponse.<CustomerDto>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message(ApiUrlConstant.CREATE)
                .data(customerToCustomerDto(saveCustomers))
                .build();
        return ResponseEntity.ok(response);

    }

//    @Override
//    public ResponseEntity<?> getAll() {
//        List<Customer> customers = customerRepository.findAll();
//        List<CustomerDto> customerDtoList = new ArrayList<>();
//        for (Customer customer:customers) {
//            customerDtoList.add(customerToCustomerDto(customer));
//        }
//            ControllerResponse<?> response = ControllerResponse.builder()
//                    .status(HttpStatus.OK.getReasonPhrase())
//                    .message(ApiUrlConstant.GETDATA)
//                    .data(customerDtoList)
//                    .build();
//
//        return ResponseEntity.ok(response);
//    }

    @Override
    public ResponseEntity<?> getById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            ControllerResponse<CustomerDto> response = ControllerResponse.<CustomerDto>builder()
                    .status(HttpStatus.OK.getReasonPhrase())
                    .message(ApiUrlConstant.GETDATA)
                    .data(customerToCustomerDto(optionalCustomer.get()))
                    .build();
            return ResponseEntity.ok(response);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");

    }

    @Override
    public ResponseEntity<?> update(String id,CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        validateId(id);
        Customer customer = optionalCustomer.get();
        customer.setName(customerDto.getName());
        customer.setGender(customerDto.getGender());
        customer.setAddress(customerDto.getAddress());
        customer.setBirthDate(customerDto.getBirthDate());
        customer.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        customerRepository.save(customer);
        ControllerResponse<CustomerDto> responseUpdate = ControllerResponse.<CustomerDto>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message(ApiUrlConstant.UPDATE)
                .data(customerToCustomerDto(optionalCustomer.get()))
                .build();
        return ResponseEntity.ok(responseUpdate);

    }

    @Override
    public ResponseEntity<?> deleteById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
        Customer customer = optionalCustomer.get();
        customerRepository.delete(customer);
        ControllerResponse<CustomerDto> response = ControllerResponse.<CustomerDto>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message(ApiUrlConstant.GETDATA)
                .data(customerToCustomerDto(optionalCustomer.get()))
                .build();
        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<?> getCustomerPerPage(Integer page, Integer size, String sortBy, String direction, CustomerDto customerDto) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Customer> customerSpecification = CustomerSpesification.getSpecification(customerDto);
        Page<Customer> allData = customerRepository.findAll(customerSpecification, pageable);
        ControllerResponse<Object> data = ControllerResponse.builder()
                .status("Success")
                .message("Success get data")
                .data(new PageResponseWrapper<>(allData))
                .build();
        return ResponseEntity.ok(data);
    }

    private Optional<Customer> validateId(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        return customerOptional;
    }

    public CustomerDto customerToCustomerDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setBirthDate(customer.getBirthDate());
        customerDto.setGender(customer.getGender());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }

}
