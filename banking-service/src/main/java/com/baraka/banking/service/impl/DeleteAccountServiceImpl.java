package com.baraka.banking.service.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.dto.DeleteAccountDto;
import com.baraka.banking.dto.RequestDto;
import com.baraka.banking.enums.AccountStatus;
import com.baraka.banking.repository.AccountRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import com.baraka.banking.service.BankingService;
import com.baraka.banking.service.BankingValidationService;
import com.baraka.banking.util.JacksonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("delete_account")
public class DeleteAccountServiceImpl implements BankingService {

    private AccountRepository accountRepository;
    private BankingValidationService bankingValidationService;

    public DeleteAccountServiceImpl(AccountRepository accountRepository,
                                    BankingValidationService bankingValidationService) {
        this.accountRepository = accountRepository;
        this.bankingValidationService = bankingValidationService;
    }

    @Transactional
    @Override
    public BankingResponse<DeleteAccountDto> call(BankingRequest request) {
        RequestDto requestDto = JacksonUtil.toObject(request.getRequest(), RequestDto.class);
        bankingValidationService.validateAccountNumber(requestDto.getAccountNumber());
        AccountBo accountBo = accountRepository.findByAccountNumber(requestDto.getAccountNumber()).get();
        accountBo.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(accountBo);

        DeleteAccountDto deleteAccountDto = DeleteAccountDto.builder()
                .customerExternalId(accountBo.getCustomerExternalId())
                .accountNumber(accountBo.getAccountNumber())
                .accountStatus(accountBo.getAccountStatus())
                .build();
        BankingResponse<DeleteAccountDto> response = new BankingResponse<>();
        response.setResponse(deleteAccountDto);
        return response;
    }
}
