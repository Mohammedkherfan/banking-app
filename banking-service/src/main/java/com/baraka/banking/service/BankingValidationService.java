package com.baraka.banking.service;

import com.baraka.banking.enums.AccountStatus;

import java.math.BigDecimal;

public interface BankingValidationService {

    void validateAccountBalance(BigDecimal transactionAmount, BigDecimal accountBalance);

    void validateAccountStatus(AccountStatus accountStatus);

    void validateAccountNumber(String accountNumber);
}
