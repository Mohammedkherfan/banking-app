package com.baraka.banking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMERS")
public class CustomerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "CUSTOMER_EXTERNAL_ID", nullable = false, unique = true)
    private String customerExternalId;

    @Column(name = "CUSTOMER_NAME", nullable = false, unique = false)
    private String customerName;

    @Column(name = "CUSTOMER_BIRTHDATE", nullable = false, unique = false)
    private LocalDate customerBirthDate;

    @Column(name = "CUSTOMER_ADDRESS", nullable = false, unique = false)
    private String customerAddress;

    @Column(name = "CUSTOMER_PHONE", nullable = false, unique = false)
    private String customerPhone;

    @Column(name = "CUSTOMER_EMAIL", nullable = false, unique = false)
    private String customerEmail;
}
