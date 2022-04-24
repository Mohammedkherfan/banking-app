package com.baraka.banking.map.impl;

import com.baraka.banking.bo.CustomerBo;
import com.baraka.banking.entity.CustomerEntity;
import com.baraka.banking.map.CustomerMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapImpl implements CustomerMap {

    @Override
    public CustomerBo toBo(CustomerEntity entity) {
        CustomerBo bo = new CustomerBo();
        BeanUtils.copyProperties(entity, bo);
        return bo;
    }

    @Override
    public CustomerEntity toEntity(CustomerBo bo) {
        CustomerEntity entity = new CustomerEntity();
        BeanUtils.copyProperties(bo, entity);
        return entity;
    }
}
