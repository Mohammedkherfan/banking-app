package com.baraka.banking.entity;

import com.baraka.banking.enums.AccountStatus;
import com.baraka.banking.enums.AccountType;
import com.baraka.banking.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNTS")
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "CUSTOMER_EXTERNAL_ID", nullable = false, unique = true)
    private String customerExternalId;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "ACCOUNT_IBAN", nullable = false, unique = true)
    private String accountIban;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_CURRENCY", nullable = false, unique = false)
    private Currency accountCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", nullable = false, unique = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_STATUS", nullable = false, unique = false)
    private AccountStatus accountStatus;

    @Column(name = "ACCOUNT_CREATED_DATE", nullable = false, unique = false)
    private LocalDateTime accountCreatedDate;

    @Column(name = "ACCOUNT_BALANCE", nullable = false, unique = false)
    private BigDecimal accountBalance;

    @Column(name = "ACCOUNT_BANK", nullable = false, unique = false)
    private String accountBank;

    @Column(name = "ACCOUNT_BRANCH", nullable = false, unique = false)
    private String accountBranch;
}
