package com.baraka.banking.dto;

import com.baraka.banking.enums.AccountStatus;
import com.baraka.banking.enums.AccountType;
import com.baraka.banking.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBalanceDto {

    private String customerExternalId;
    private String customerName;
    private LocalDate customerBirthDate;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String accountNumber;
    private String accountIban;
    private Currency accountCurrency;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private LocalDateTime accountCreatedDate;
    private BigDecimal accountBalance;
    private String accountBranch;
}
