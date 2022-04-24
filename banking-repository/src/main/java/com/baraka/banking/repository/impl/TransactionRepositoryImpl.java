package com.baraka.banking.repository.impl;

import com.baraka.banking.bo.TransactionBo;
import com.baraka.banking.jpa.TransactionJpaRepository;
import com.baraka.banking.map.TransactionMap;
import com.baraka.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private TransactionMap map;
    @Autowired
    private TransactionJpaRepository repository;

    @Override
    public TransactionBo save(TransactionBo transactionBo) {
        return map.toBo(repository.save(map.toEntity(transactionBo)));
    }
}
