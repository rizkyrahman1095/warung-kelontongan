package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.dto.request.PurchaseDto;
import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.entity.Purchase;
import com.enigma.tokonyadia.entity.PurchaseDetail;
import com.enigma.tokonyadia.repository.CustomerRepository;
import com.enigma.tokonyadia.repository.PurchaseRepository;
import com.enigma.tokonyadia.service.CustomerService;
import com.enigma.tokonyadia.service.ProductService;
import com.enigma.tokonyadia.service.PurchaseDetailService;
import com.enigma.tokonyadia.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final PurchaseDetailService purchaseDetailService;
    private final CustomerRepository customerRepository;
    private final ProductService productService;

    @Override
    public ResponseEntity<?> saveTransaction(PurchaseDto purchase) {
        return null;
    }
}


