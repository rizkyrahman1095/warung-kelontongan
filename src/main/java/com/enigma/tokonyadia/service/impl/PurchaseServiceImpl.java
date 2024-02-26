package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.dto.request.PurchaseDetailDto;
import com.enigma.tokonyadia.dto.request.PurchaseDto;
import com.enigma.tokonyadia.dto.response.ControllerResponse;
import com.enigma.tokonyadia.entity.Customer;
import com.enigma.tokonyadia.entity.Product;
import com.enigma.tokonyadia.entity.Purchase;
import com.enigma.tokonyadia.entity.PurchaseDetail;
import com.enigma.tokonyadia.repository.CustomerRepository;
import com.enigma.tokonyadia.repository.ProductRepository;
import com.enigma.tokonyadia.repository.PurchaseRepository;
import com.enigma.tokonyadia.service.PurchaseDetailService;
import com.enigma.tokonyadia.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final PurchaseDetailService purchaseDetailService;

    @Override
    @Transactional
    public ResponseEntity<?> saveTransaction(PurchaseDto purchaseDto) {
        try {
            log.info("Request in {}", purchaseDto);

            // validate customer
            Optional<Customer> customer = customerRepo.findById(purchaseDto.getCustomerId());
            if (customer.isEmpty()) {
                ControllerResponse<Object> customerNotFound = ControllerResponse.builder()
                        .status("Failed")
                        .message("Failed")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customerNotFound);
            }

            Purchase newPurchase = Purchase.builder()
                    .purchaseDate(purchaseDto.getPurchaseDate())
                    .customer(customer.get())
                    .build();

            List<PurchaseDetail> purchaseDetail = new ArrayList<>();
            for (PurchaseDetailDto purchaseDetailDto : purchaseDto.getPurchaseDetailDtoList()) {
                String productId = purchaseDetailDto.getProductId();

                // validate product exist
                Optional<Product> product = productRepo.findById(productId);
                if (product.isEmpty()) {
                    ControllerResponse<Object> productFailed = ControllerResponse.builder()
                            .status("Failed")
                            .message("The product not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productFailed);
                }

                // validate product stock
                if (product.get().getStock() < purchaseDetailDto.getQty()) {
                    ControllerResponse<Object> productFailed = ControllerResponse.builder()
                            .status("Failed")
                            .message("The product out of stock!")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productFailed);
                }
                PurchaseDetail newPurchaseDetail = PurchaseDetail.builder()
                        .quantity(purchaseDetailDto.getQty())
                        .priceSell(product.get().getPrice())
                        .product(product.get())
                        .purchase(newPurchase)
                        .build();
                purchaseDetail.add(newPurchaseDetail);
                product.get().setStock(product.get().getStock() - purchaseDetailDto.getQty());
                purchaseDetailService.savePurchaseDetail(newPurchaseDetail);
                productRepo.save(product.get());
            }
            newPurchase.setPurchaseDetails(purchaseDetail);
            purchaseRepo.save(newPurchase);
            ControllerResponse<Purchase> successPurchase = ControllerResponse.<Purchase>builder()
                    .status("Success")
                    .message("Success")
                    .data(newPurchase)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(successPurchase);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            ControllerResponse<Object> failedPurchase = ControllerResponse.builder()
                    .status("Failed")
                    .message("Failed to purchase, please try again later!")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failedPurchase);
        }
    }

}
