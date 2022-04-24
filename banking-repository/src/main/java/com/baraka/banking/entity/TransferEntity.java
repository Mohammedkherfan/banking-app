package com.baraka.banking.entity;

import com.baraka.banking.enums.TransferType;
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
@Table(name = "TRANSFERS")
public class TransferEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "CUSTOMER_EXTERNAL_ID", nullable = false, unique = false)
    private String customerExternalId;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, unique = false)
    private String accountNumber;

    @Column(name = "PAY_ACCOUNT_NUMBER", nullable = false, unique = false)
    private String payAccountNumber;

    @Column(name = "PAY_IBAN", nullable = false, unique = false)
    private String payIban;

    @Column(name = "PAY_BANK", nullable = false, unique = false)
    private String payBank;

    @Column(name = "PAY_BRANCH", nullable = false, unique = false)
    private String payBranch;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSFER_TYPE", nullable = false, unique = false)
    private TransferType transferType;

    @Column(name = "TRANSFER_AMOUNT", nullable = false, unique = false)
    private BigDecimal transferAmount;

    @Column(name = "TRANSFER_DATE", nullable = false, unique = false)
    private LocalDateTime transferDate;

    @Column(name = "TRANSACTION_EXTERNAL_ID", nullable = false, unique = true)
    private String transactionExternalId;

    @Column(name = "TRANSFER_EXTERNAL_ID", nullable = false, unique = true)
    private String transferExternalId;
}
