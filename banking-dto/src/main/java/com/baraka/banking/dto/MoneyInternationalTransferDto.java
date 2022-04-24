package com.baraka.banking.dto;

import com.baraka.banking.enums.TransferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoneyInternationalTransferDto {

    private String customerExternalId;
    private String accountNumber;
    private String transactionExternalId;
    private String transferExternalId;
    private String payAccountNumber;
    private String payIban;
    private String payBank;
    private String payBranch;
    private TransferType transferType;
    private BigDecimal transferAmount;
    private LocalDateTime transferDate;
}
