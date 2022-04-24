package com.baraka.banking.service.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.bo.TransactionBo;
import com.baraka.banking.dto.MoneyWithdrawalDto;
import com.baraka.banking.dto.RequestDto;
import com.baraka.banking.enums.TransactionType;
import com.baraka.banking.repository.AccountRepository;
import com.baraka.banking.repository.TransactionRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import com.baraka.banking.service.BankingService;
import com.baraka.banking.service.BankingValidationService;
import com.baraka.banking.util.JacksonUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service("money_withdrawal")
public class MoneyWithdrawalServiceImpl implements BankingService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private BankingValidationService bankingValidationService;

    public MoneyWithdrawalServiceImpl(AccountRepository accountRepository,
                                      TransactionRepository transactionRepository,
                                      BankingValidationService bankingValidationService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.bankingValidationService = bankingValidationService;
    }

    @Override
    public BankingResponse<MoneyWithdrawalDto> call(BankingRequest request) {
        RequestDto requestDto = JacksonUtil.toObject(request.getRequest(), RequestDto.class);
        bankingValidationService.validateAccountNumber(requestDto.getAccountNumber());
        AccountBo accountBo = accountRepository.findByAccountNumber(requestDto.getAccountNumber()).get();
        bankingValidationService.validateAccountStatus(accountBo.getAccountStatus());
        bankingValidationService.validateAccountBalance(requestDto.getTransactionAmount(), accountBo.getAccountBalance());
        TransactionBo transactionBo = transactionRepository.save(TransactionBo.builder()
                .customerExternalId(accountBo.getCustomerExternalId())
                .accountNumber(accountBo.getAccountNumber())
                .transactionType(TransactionType.WITHDRAWAL)
                .transactionExternalId(UUID.randomUUID().toString())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(requestDto.getTransactionAmount())
                .build());
        accountBo.setAccountBalance(accountBo.getAccountBalance().subtract(requestDto.getTransactionAmount()));
        accountRepository.save(accountBo);

        MoneyWithdrawalDto moneyWithdrawalDto = MoneyWithdrawalDto.builder()
                .customerExternalId(accountBo.getCustomerExternalId())
                .accountBalance(accountBo.getAccountBalance())
                .accountNumber(accountBo.getAccountNumber())
                .transactionType(transactionBo.getTransactionType())
                .transactionExternalId(transactionBo.getTransactionExternalId())
                .transactionAmount(transactionBo.getTransactionAmount())
                .build();
        BankingResponse<MoneyWithdrawalDto> response = new BankingResponse<>();
        response.setResponse(moneyWithdrawalDto);
        return response;
    }
}
