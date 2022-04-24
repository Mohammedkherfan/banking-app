package com.baraka.banking.dto;

import com.baraka.banking.enums.AccountType;
import com.baraka.banking.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDto {

    private String customerName;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate customerBirthDate;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String accountNumber;
    private AccountType accountType;
    private Currency accountCurrency;
    private String accountBranch;
    private BigDecimal transactionAmount;
    private String payAccountNumber;
    private String payIban;
    private String payBank;
    private String payBranch;
    private BigDecimal transferAmount;
}
