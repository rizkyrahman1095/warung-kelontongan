package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.entity.Purchase;
import com.enigma.tokonyadia.service.PurchaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/transactions")
    public Purchase savePurchase(@RequestBody Purchase purchase){
        return purchaseService.saveTransaction(purchase);
    }
}
