package com.baraka.banking.manager.impl;

import com.baraka.banking.manager.BankingManager;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import com.baraka.banking.service.BankingService;
import com.baraka.banking.util.BankingUtil;
import com.baraka.banking.validation.BankingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BankingManagerImpl implements BankingManager {

    private ApplicationContext applicationContext;
    private BankingValidation bankingValidation;

    @Autowired
    public BankingManagerImpl(ApplicationContext applicationContext,
                              BankingValidation bankingValidation) {
        this.applicationContext = applicationContext;
        this.bankingValidation = bankingValidation;
    }

    @Override
    public BankingResponse call(BankingRequest request) {
        String operation = BankingUtil.getOperation(request.getRequest());
        bankingValidation.validate(request);
        BankingService bankingService = (BankingService) applicationContext.getBean(operation);
        BankingResponse response = bankingService.call(request);
        return response;
    }
}
