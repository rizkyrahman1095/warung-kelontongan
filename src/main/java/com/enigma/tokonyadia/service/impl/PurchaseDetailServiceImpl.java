package com.enigma.tokonyadia.service.impl;


import com.enigma.tokonyadia.entity.PurchaseDetail;
import com.enigma.tokonyadia.repository.PurchaseDetailRepository;
import com.enigma.tokonyadia.service.PurchaseDetailService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

    private final PurchaseDetailRepository purchaseDetailRepository;

    public PurchaseDetailServiceImpl(PurchaseDetailRepository purchaseDetailRepository) {
        this.purchaseDetailRepository = purchaseDetailRepository;
    }

    @Override
    public PurchaseDetail savePurchaseDetail(PurchaseDetail purchaseDetail) {
        return purchaseDetailRepository.save(purchaseDetail);
    }
}
