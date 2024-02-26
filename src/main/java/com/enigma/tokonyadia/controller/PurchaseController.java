package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.dto.request.PurchaseDto;
import com.enigma.tokonyadia.entity.Purchase;
import com.enigma.tokonyadia.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> savePurchase(@RequestBody PurchaseDto purchaseDto){
        return purchaseService.saveTransaction(purchaseDto);
    }
}
