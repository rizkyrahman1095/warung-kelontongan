package com.enigma.tokonyadia.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String address;
    private String gender;
    private String phoneNumber;


}

