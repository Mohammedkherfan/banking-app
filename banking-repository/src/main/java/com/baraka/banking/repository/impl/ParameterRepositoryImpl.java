package com.baraka.banking.repository.impl;

import com.baraka.banking.bo.ParameterBo;
import com.baraka.banking.entity.ParameterEntity;
import com.baraka.banking.jpa.ParameterJpaRepository;
import com.baraka.banking.map.ParameterMap;
import com.baraka.banking.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParameterRepositoryImpl implements ParameterRepository {

    @Autowired
    private ParameterMap map;
    @Autowired
    private ParameterJpaRepository repository;

    @Override
    public List<ParameterBo> findAllByOperation(String operation) {
        List<ParameterEntity> entities = repository.findAllByOperation(operation);
        return entities.stream().map(entity -> map.toBo(entity)).collect(Collectors.toList());
    }
}
