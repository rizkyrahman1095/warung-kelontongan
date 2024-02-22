package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Purchase;
import com.enigma.tokonyadia.entity.PurchaseDetail;
import com.enigma.tokonyadia.repository.PurchaseRepository;
import com.enigma.tokonyadia.service.PurchaseDetailService;
import com.enigma.tokonyadia.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final PurchaseDetailService purchaseDetailService;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, PurchaseDetailService purchaseDetailService) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseDetailService = purchaseDetailService;
    }

    @Override
    @Transactional
    public Purchase saveTransaction(Purchase purchase) {
        Purchase purchase1 = purchaseRepository.save(purchase);
        for (PurchaseDetail purchaseDetail : purchase.getPurchaseDetails()){
            purchaseDetail.setPurchase(purchase1);
            purchaseDetailService.savePurchaseDetail(purchaseDetail);
        }
        return purchase1;
    }
}
