package com.baraka.banking.service.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.bo.TransactionBo;
import com.baraka.banking.bo.TransferBo;
import com.baraka.banking.dto.MoneyInternationalTransferDto;
import com.baraka.banking.dto.RequestDto;
import com.baraka.banking.enums.TransactionType;
import com.baraka.banking.enums.TransferType;
import com.baraka.banking.repository.AccountRepository;
import com.baraka.banking.repository.TransactionRepository;
import com.baraka.banking.repository.TransferRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import com.baraka.banking.service.BankingService;
import com.baraka.banking.service.BankingValidationService;
import com.baraka.banking.util.JacksonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service("money_international_transfer")
public class MoneyInternationalTransferServiceImpl implements BankingService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private TransferRepository transferRepository;
    private BankingValidationService bankingValidationService;

    public MoneyInternationalTransferServiceImpl(AccountRepository accountRepository,
                                                 TransactionRepository transactionRepository,
                                                 TransferRepository transferRepository,
                                                 BankingValidationService bankingValidationService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transferRepository = transferRepository;
        this.bankingValidationService = bankingValidationService;
    }

    @Transactional
    @Override
    public BankingResponse<MoneyInternationalTransferDto> call(BankingRequest request) {
        RequestDto requestDto = JacksonUtil.toObject(request.getRequest(), RequestDto.class);
        bankingValidationService.validateAccountNumber(requestDto.getAccountNumber());
        AccountBo accountBo = accountRepository.findByAccountNumber(requestDto.getAccountNumber()).get();
        bankingValidationService.validateAccountStatus(accountBo.getAccountStatus());
        bankingValidationService.validateAccountBalance(requestDto.getTransferAmount(), accountBo.getAccountBalance());

        TransactionBo transactionBo = transactionRepository.save(TransactionBo.builder()
                .customerExternalId(accountBo.getCustomerExternalId())
                .accountNumber(accountBo.getAccountNumber())
                .transactionType(TransactionType.TRANSFER)
                .transactionExternalId(UUID.randomUUID().toString())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(requestDto.getTransferAmount())
                .build());

        TransferBo transferBo = transferRepository.save(TransferBo.builder()
                .customerExternalId(accountBo.getCustomerExternalId())
                .accountNumber(accountBo.getAccountNumber())
                .transactionExternalId(transactionBo.getTransactionExternalId())
                .transferExternalId(UUID.randomUUID().toString())
                .payAccountNumber(requestDto.getPayAccountNumber())
                .payIban(requestDto.getPayIban())
                .payBank(requestDto.getPayBank())
                .payBranch(requestDto.getPayBranch())
                .transferType(TransferType.INTERNATIONAL)
                .transferAmount(requestDto.getTransferAmount())
                .transferDate(LocalDateTime.now())
                .build());
        accountBo.setAccountBalance(accountBo.getAccountBalance().subtract(transferBo.getTransferAmount()));
        accountRepository.save(accountBo);

        MoneyInternationalTransferDto moneyInternationalTransferDto = MoneyInternationalTransferDto.builder()
                .customerExternalId(accountBo.getCustomerExternalId())
                .accountNumber(accountBo.getAccountNumber())
                .transactionExternalId(transactionBo.getTransactionExternalId())
                .transferExternalId(transferBo.getTransferExternalId())
                .payAccountNumber(transferBo.getPayAccountNumber())
                .payIban(transferBo.getPayIban())
                .payBank(transferBo.getPayBank())
                .payBranch(transferBo.getPayBranch())
                .transferType(transferBo.getTransferType())
                .transferAmount(transferBo.getTransferAmount())
                .transferDate(transferBo.getTransferDate())
                .build();
        BankingResponse<MoneyInternationalTransferDto> response = new BankingResponse<>();
        response.setResponse(moneyInternationalTransferDto);
        return response;
    }

}
