package com.enigma.tokonyadia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerSearchDTO {
    private String customerFullName;
    private String customerAddress;
    private Date customerBirthDate;
}

