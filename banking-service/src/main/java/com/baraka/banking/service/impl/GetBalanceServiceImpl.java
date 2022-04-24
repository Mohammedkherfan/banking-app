package com.baraka.banking.service.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.bo.CustomerBo;
import com.baraka.banking.dto.GetBalanceDto;
import com.baraka.banking.dto.RequestDto;
import com.baraka.banking.repository.AccountRepository;
import com.baraka.banking.repository.CustomerRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import com.baraka.banking.service.BankingService;
import com.baraka.banking.service.BankingValidationService;
import com.baraka.banking.util.JacksonUtil;
import org.springframework.stereotype.Service;

@Service("get_balance")
public class GetBalanceServiceImpl implements BankingService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private BankingValidationService bankingValidationService;

    public GetBalanceServiceImpl(CustomerRepository customerRepository,
                                 AccountRepository accountRepository,
                                 BankingValidationService bankingValidationService) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.bankingValidationService = bankingValidationService;
    }

    @Override
    public BankingResponse<GetBalanceDto> call(BankingRequest request) {
        RequestDto requestDto = JacksonUtil.toObject(request.getRequest(), RequestDto.class);
        bankingValidationService.validateAccountNumber(requestDto.getAccountNumber());
        AccountBo accountBo = accountRepository.findByAccountNumber(requestDto.getAccountNumber()).get();
        CustomerBo customerBo = customerRepository.findByCustomerExternalId(accountBo.getCustomerExternalId()).get();

        GetBalanceDto getBalanceDto = GetBalanceDto.builder()
                .customerExternalId(customerBo.getCustomerExternalId())
                .customerName(customerBo.getCustomerName())
                .customerBirthDate(customerBo.getCustomerBirthDate())
                .customerAddress(customerBo.getCustomerAddress())
                .customerPhone(customerBo.getCustomerPhone())
                .customerEmail(customerBo.getCustomerEmail())
                .accountNumber(accountBo.getAccountNumber())
                .accountIban(accountBo.getAccountIban())
                .accountCurrency(accountBo.getAccountCurrency())
                .accountType(accountBo.getAccountType())
                .accountStatus(accountBo.getAccountStatus())
                .accountCreatedDate(accountBo.getAccountCreatedDate())
                .accountBalance(accountBo.getAccountBalance())
                .accountBranch(accountBo.getAccountBranch())
                .build();
        BankingResponse<GetBalanceDto> response = new BankingResponse<>();
        response.setResponse(getBalanceDto);
        return response;
    }
}
