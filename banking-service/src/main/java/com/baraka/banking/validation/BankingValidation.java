package com.baraka.banking.validation;

import com.baraka.banking.request.BankingRequest;

public interface BankingValidation {

    void validate(BankingRequest request);
}
