package com.baraka.banking.bo;

import com.baraka.banking.enums.AccountStatus;
import com.baraka.banking.enums.AccountType;
import com.baraka.banking.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBo {

    private Long id;
    private String customerExternalId;
    private String accountNumber;
    private String accountIban;
    private Currency accountCurrency;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private LocalDateTime accountCreatedDate;
    private BigDecimal accountBalance;
    private String accountBank;
    private String accountBranch;
}
