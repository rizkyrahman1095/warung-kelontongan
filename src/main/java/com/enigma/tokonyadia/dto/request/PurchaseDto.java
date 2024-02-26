package com.enigma.tokonyadia.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PurchaseDto {
    private Date purchaseDate;
    private String customerId;
    private List<PurchaseDetailDto> purchaseDetailDtoList =new ArrayList<>();
}
