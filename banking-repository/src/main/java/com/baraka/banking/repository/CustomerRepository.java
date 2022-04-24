package com.baraka.banking.repository;

import com.baraka.banking.bo.CustomerBo;

import java.util.Optional;

public interface CustomerRepository {

    CustomerBo save(CustomerBo customerBo);

    Optional<CustomerBo> findByCustomerExternalId(String customerExternalId);
}
