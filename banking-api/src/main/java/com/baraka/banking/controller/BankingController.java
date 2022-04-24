package com.baraka.banking.controller;

import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.response.BankingResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = "/bank")
public interface BankingController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Method for banking operation.", notes = "This method used when you want to send banking operation.")
    @ApiParam(value = "The parameter for this operation.", name = "bankingRequest")
    BankingResponse call(BankingRequest request);
}
