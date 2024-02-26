package com.enigma.tokonyadia.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto {
    private String productName;
    private Integer productStock;
    private Integer productPrice;

}
