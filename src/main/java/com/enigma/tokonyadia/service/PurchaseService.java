package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.dto.request.PurchaseDto;
import com.enigma.tokonyadia.entity.Purchase;
import org.springframework.http.ResponseEntity;

public interface PurchaseService {
    ResponseEntity<?> saveTransaction(PurchaseDto purchaseDto);
}
