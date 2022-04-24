package com.baraka.banking.map.impl;

import com.baraka.banking.bo.TransactionBo;
import com.baraka.banking.entity.TransactionEntity;
import com.baraka.banking.map.TransactionMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapImpl implements TransactionMap {

    @Override
    public TransactionBo toBo(TransactionEntity entity) {
        TransactionBo bo = new TransactionBo();
        BeanUtils.copyProperties(entity, bo);
        return bo;
    }

    @Override
    public TransactionEntity toEntity(TransactionBo bo) {
        TransactionEntity entity = new TransactionEntity();
        BeanUtils.copyProperties(bo, entity);
        return entity;
    }
}
