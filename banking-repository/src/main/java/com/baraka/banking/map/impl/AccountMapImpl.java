package com.baraka.banking.map.impl;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.entity.AccountEntity;
import com.baraka.banking.map.AccountMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountMapImpl implements AccountMap {

    @Override
    public AccountBo toBo(AccountEntity entity) {
        AccountBo bo = new AccountBo();
        BeanUtils.copyProperties(entity, bo);
        return bo;
    }

    @Override
    public AccountEntity toEntity(AccountBo bo) {
        AccountEntity entity = new AccountEntity();
        BeanUtils.copyProperties(bo, entity);
        return entity;
    }
}
