package com.baraka.banking.map.impl;

import com.baraka.banking.bo.TransferBo;
import com.baraka.banking.entity.TransferEntity;
import com.baraka.banking.map.TransferMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TransferMapImpl implements TransferMap {

    @Override
    public TransferBo toBo(TransferEntity entity) {
        TransferBo bo = new TransferBo();
        BeanUtils.copyProperties(entity, bo);
        return bo;
    }

    @Override
    public TransferEntity toEntity(TransferBo bo) {
        TransferEntity entity = new TransferEntity();
        BeanUtils.copyProperties(bo, entity);
        return entity;
    }
}
