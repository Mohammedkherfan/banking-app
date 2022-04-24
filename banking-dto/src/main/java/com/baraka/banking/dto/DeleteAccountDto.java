package com.baraka.banking.dto;

import com.baraka.banking.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountDto {

    private String customerExternalId;
    private String accountNumber;
    private AccountStatus accountStatus;
}
