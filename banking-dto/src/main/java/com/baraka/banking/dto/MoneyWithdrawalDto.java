package com.baraka.banking.dto;

import com.baraka.banking.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyWithdrawalDto {

    private String customerExternalId;
    private String accountNumber;
    private String transactionExternalId;
    private TransactionType transactionType;
    private BigDecimal transactionAmount;
    private BigDecimal accountBalance;
}
