package com.enigma.tokonyadia.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PurchaseDetailDto {
    private String productId;
    private Integer qty;

}
