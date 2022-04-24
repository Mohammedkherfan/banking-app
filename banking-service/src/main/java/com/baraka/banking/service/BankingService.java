package com.baraka.banking.service;

import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;

public interface BankingService {

    BankingResponse call(BankingRequest request);
}
