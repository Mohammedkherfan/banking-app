package com.baraka.banking.manager;

import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;

public interface BankingManager {

    BankingResponse call(BankingRequest request);
}
