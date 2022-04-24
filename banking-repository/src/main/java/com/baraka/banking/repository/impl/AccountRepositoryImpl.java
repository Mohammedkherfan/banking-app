package com.baraka.banking.repository.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.entity.AccountEntity;
import com.baraka.banking.jpa.AccountJpaRepository;
import com.baraka.banking.map.AccountMap;
import com.baraka.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private AccountMap map;
    @Autowired
    private AccountJpaRepository repository;

    @Override
    public AccountBo save(AccountBo accountBo) {
        return map.toBo(repository.save(map.toEntity(accountBo)));
    }

    @Override
    public Boolean existsByAccountNumber(String accountNumber) {
        return repository.existsByAccountNumber(accountNumber);
    }

    @Override
    public Optional<AccountBo> findByAccountNumber(String accountNumber) {
        AccountEntity entity = repository.findByAccountNumber(accountNumber);
        if (isNull(entity))
            return Optional.empty();
        else
            return Optional.of(map.toBo(entity));
    }

    @Override
    public List<AccountBo> findAll() {
        return repository.findAll().stream().map(entity -> map.toBo(entity)).collect(Collectors.toList());
    }
}
