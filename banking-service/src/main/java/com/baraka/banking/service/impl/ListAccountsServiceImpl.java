package com.baraka.banking.service.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.bo.CustomerBo;
import com.baraka.banking.dto.ListAccountsDto;
import com.baraka.banking.repository.AccountRepository;
import com.baraka.banking.repository.CustomerRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import com.baraka.banking.service.BankingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("list_accounts")
public class ListAccountsServiceImpl implements BankingService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    public ListAccountsServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public BankingResponse<List<ListAccountsDto>> call(BankingRequest request) {
        List<ListAccountsDto> list = new ArrayList<>();
        List<AccountBo> accounts = accountRepository.findAll();
        accounts.forEach(accountBo -> {
            CustomerBo customerBo = customerRepository.findByCustomerExternalId(accountBo.getCustomerExternalId()).get();
            list.add(ListAccountsDto.builder()
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
                    .build());
        });

        BankingResponse<List<ListAccountsDto>> response = new BankingResponse<>();
        response.setResponse(list);
        return response;
    }
}
