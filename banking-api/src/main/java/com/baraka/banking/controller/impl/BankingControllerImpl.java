package com.baraka.banking.controller.impl;

import com.baraka.banking.controller.BankingController;
import com.baraka.banking.manager.BankingManager;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "Banking App Api")
public class BankingControllerImpl implements BankingController {

    private BankingManager bankingManager;

    @Autowired
    public BankingControllerImpl(BankingManager bankingManager) {
        this.bankingManager = bankingManager;
    }

    @Override
    public BankingResponse call(@RequestBody @Valid BankingRequest request) {
        return bankingManager.call(request);
    }
}
