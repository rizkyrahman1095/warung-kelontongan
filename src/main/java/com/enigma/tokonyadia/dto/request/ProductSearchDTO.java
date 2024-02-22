package com.enigma.tokonyadia.dto.request;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class ProductSearchDTO {
    private String productName;
    private Integer productStock;
    private Integer productPrice;

    public ProductSearchDTO(String productName, Integer productStock, Integer productPrice) {
        this.productName = productName;
        this.productStock = productStock;
        this.productPrice = productPrice;
    }
}
