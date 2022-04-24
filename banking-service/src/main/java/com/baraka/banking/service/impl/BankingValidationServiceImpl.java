package com.baraka.banking.service.impl;

import com.baraka.banking.enums.AccountStatus;
import com.baraka.banking.exception.BankingException;
import com.baraka.banking.repository.AccountRepository;
import com.baraka.banking.service.BankingValidationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankingValidationServiceImpl implements BankingValidationService {

    private AccountRepository accountRepository;

    public BankingValidationServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void validateAccountBalance(BigDecimal transactionAmount, BigDecimal accountBalance) {
        if (transactionAmount.compareTo(accountBalance) == 1)
            throw new BankingException(String.format("Insufficient fund ", transactionAmount));
    }

    @Override
    public void validateAccountStatus(AccountStatus accountStatus) {
        if (!AccountStatus.ACTIVE.equals(accountStatus))
            throw new BankingException(String.format("Account not active %1s ", accountStatus));
    }
    @Override
    public void validateAccountNumber(String accountNumber) {
        Boolean isExist = accountRepository.existsByAccountNumber(accountNumber);
        if (!isExist)
            throw new BankingException(String.format("Account %1s not exist", accountNumber));
    }
}
