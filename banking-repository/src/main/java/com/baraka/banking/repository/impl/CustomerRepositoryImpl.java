package com.baraka.banking.repository.impl;

import com.baraka.banking.bo.CustomerBo;
import com.baraka.banking.entity.CustomerEntity;
import com.baraka.banking.jpa.CustomerJpaRepository;
import com.baraka.banking.map.CustomerMap;
import com.baraka.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private CustomerMap map;
    @Autowired
    private CustomerJpaRepository repository;

    @Override
    public CustomerBo save(CustomerBo customerBo) {
        return map.toBo(repository.save(map.toEntity(customerBo)));
    }

    @Override
    public Optional<CustomerBo> findByCustomerExternalId(String customerExternalId) {
        CustomerEntity entity = repository.findByCustomerExternalId(customerExternalId);
        if (isNull(entity))
            return Optional.empty();
        else
            return Optional.of(map.toBo(entity));
    }
}
