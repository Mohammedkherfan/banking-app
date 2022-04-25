package com.baraka.banking.service.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.bo.CustomerBo;
import com.baraka.banking.dto.CreateAccountDto;
import com.baraka.banking.dto.RequestDto;
import com.baraka.banking.enums.AccountStatus;
import com.baraka.banking.repository.AccountRepository;
import com.baraka.banking.repository.CustomerRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import com.baraka.banking.service.BankingService;
import com.baraka.banking.util.JacksonUtil;
import com.baraka.banking.util.RandomStringGeneratorUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service("create_account")
public class CreateAccountServiceImpl implements BankingService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    public CreateAccountServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public BankingResponse<CreateAccountDto> call(BankingRequest request) {
        RequestDto requestDto = JacksonUtil.toObject(request.getRequest(), RequestDto.class);
        CustomerBo customerBo = customerRepository.save(CustomerBo.builder()
                    .customerExternalId(UUID.randomUUID().toString())
                    .customerName(requestDto.getCustomerName())
                    .customerBirthDate(requestDto.getCustomerBirthDate())
                    .customerAddress(requestDto.getCustomerAddress())
                    .customerPhone(requestDto.getCustomerPhone())
                    .customerEmail(requestDto.getCustomerEmail())
                    .build());
        String accountNumber = RandomStringGeneratorUtil.generate(14, Boolean.FALSE, Boolean.TRUE);
        AccountBo accountBo = accountRepository.save(AccountBo.builder()
                    .customerExternalId(customerBo.getCustomerExternalId())
                    .accountNumber(accountNumber)
                    .accountIban("AE" + RandomStringGeneratorUtil.generate(8, Boolean.FALSE, Boolean.TRUE) + accountNumber)
                    .accountCurrency(requestDto.getAccountCurrency())
                    .accountType(requestDto.getAccountType())
                    .accountStatus(AccountStatus.ACTIVE)
                    .accountCreatedDate(LocalDateTime.now())
                    .accountBalance(BigDecimal.ZERO)
                    .accountBranch(requestDto.getAccountBranch())
                    .accountBank("DEFAULT BANK NAME")
                    .build());

        CreateAccountDto createAccountDto = CreateAccountDto.builder()
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
        BankingResponse<CreateAccountDto> response = new BankingResponse<>();
        response.setResponse(createAccountDto);
        return response;
    }
}
