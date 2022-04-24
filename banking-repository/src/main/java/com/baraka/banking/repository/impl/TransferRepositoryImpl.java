package com.baraka.banking.repository.impl;

import com.baraka.banking.bo.TransferBo;
import com.baraka.banking.jpa.TransferJpaRepository;
import com.baraka.banking.map.TransferMap;
import com.baraka.banking.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferRepositoryImpl implements TransferRepository {

    @Autowired
    private TransferMap map;
    @Autowired
    private TransferJpaRepository repository;

    @Override
    public TransferBo save(TransferBo transferBo) {
        return map.toBo(repository.save(map.toEntity(transferBo)));
    }
}
