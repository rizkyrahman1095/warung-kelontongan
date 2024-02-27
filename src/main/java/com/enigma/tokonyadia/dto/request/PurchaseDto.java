package com.enigma.tokonyadia.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


@AllArgsConstructor
@Data
@Builder
public class PurchaseDto {
    private final Date purchaseDate;
    private final String customerId;
    private final List<PurchaseDetailDto> purchaseDetailDtoList =new ArrayList<>();
}
