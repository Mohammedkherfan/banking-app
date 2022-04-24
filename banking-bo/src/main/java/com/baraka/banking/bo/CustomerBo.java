package com.baraka.banking.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBo {

    private Long id;
    private String customerExternalId;
    private String customerName;
    private LocalDate customerBirthDate;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
}
