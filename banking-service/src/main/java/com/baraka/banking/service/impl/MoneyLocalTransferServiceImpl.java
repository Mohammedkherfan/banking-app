package com.baraka.banking.service.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.bo.TransactionBo;
import com.baraka.banking.bo.TransferBo;
import com.baraka.banking.dto.MoneyLocalTransferDto;
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

@Service("money_local_transfer")
public class MoneyLocalTransferServiceImpl implements BankingService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private TransferRepository transferRepository;
    private BankingValidationService bankingValidationService;

    public MoneyLocalTransferServiceImpl(AccountRepository accountRepository,
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
    public BankingResponse<MoneyLocalTransferDto> call(BankingRequest request) {
        RequestDto requestDto = JacksonUtil.toObject(request.getRequest(), RequestDto.class);
        bankingValidationService.validateAccountNumber(requestDto.getAccountNumber());
        bankingValidationService.validateAccountNumber(requestDto.getPayAccountNumber());

        AccountBo bdfAccountBo = accountRepository.findByAccountNumber(requestDto.getAccountNumber()).get();
        AccountBo payAccountBo = accountRepository.findByAccountNumber(requestDto.getPayAccountNumber()).get();

        bankingValidationService.validateAccountStatus(bdfAccountBo.getAccountStatus());
        bankingValidationService.validateAccountStatus(payAccountBo.getAccountStatus());

        bankingValidationService.validateAccountBalance(requestDto.getTransferAmount(), bdfAccountBo.getAccountBalance());

        TransactionBo transactionBo = transactionRepository.save(TransactionBo.builder()
                .customerExternalId(bdfAccountBo.getCustomerExternalId())
                .accountNumber(bdfAccountBo.getAccountNumber())
                .transactionType(TransactionType.TRANSFER)
                .transactionExternalId(UUID.randomUUID().toString())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(requestDto.getTransferAmount())
                .build());

        TransferBo transferBo = transferRepository.save(TransferBo.builder()
                .customerExternalId(bdfAccountBo.getCustomerExternalId())
                .accountNumber(bdfAccountBo.getAccountNumber())
                .transactionExternalId(transactionBo.getTransactionExternalId())
                .transferExternalId(UUID.randomUUID().toString())
                .payAccountNumber(payAccountBo.getAccountNumber())
                .payIban(payAccountBo.getAccountIban())
                .payBank(payAccountBo.getAccountBank())
                .payBranch(payAccountBo.getAccountBranch())
                .transferType(TransferType.LOCAL)
                .transferAmount(requestDto.getTransferAmount())
                .transferDate(LocalDateTime.now())
                .build());

        bdfAccountBo.setAccountBalance(bdfAccountBo.getAccountBalance().subtract(transferBo.getTransferAmount()));
        accountRepository.save(bdfAccountBo);

        payAccountBo.setAccountBalance(payAccountBo.getAccountBalance().add(transferBo.getTransferAmount()));
        accountRepository.save(payAccountBo);

        MoneyLocalTransferDto moneyLocalTransferDto = MoneyLocalTransferDto.builder()
                .customerExternalId(bdfAccountBo.getCustomerExternalId())
                .accountNumber(bdfAccountBo.getAccountNumber())
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
        BankingResponse<MoneyLocalTransferDto> response = new BankingResponse<>();
        response.setResponse(moneyLocalTransferDto);
        return response;
    }
}
