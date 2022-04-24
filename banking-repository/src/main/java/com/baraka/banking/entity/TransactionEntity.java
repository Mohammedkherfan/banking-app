package com.baraka.banking.entity;

import com.baraka.banking.enums.TransactionType;
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
@Table(name = "TRANSACTIONS")
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "CUSTOMER_EXTERNAL_ID", nullable = false, unique = false)
    private String customerExternalId;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, unique = false)
    private String accountNumber;

    @Column(name = "TRANSACTION_EXTERNAL_ID", nullable = false, unique = true)
    private String transactionExternalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_TYPE", nullable = false, unique = false)
    private TransactionType transactionType;

    @Column(name = "TRANSACTION_AMOUNT", nullable = false, unique = false)
    private BigDecimal transactionAmount;

    @Column(name = "TRANSACTION_DATE", nullable = false, unique = false)
    private LocalDateTime transactionDate;
}
