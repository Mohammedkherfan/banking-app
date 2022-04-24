package com.baraka.banking.map.impl;

import com.baraka.banking.bo.ParameterBo;
import com.baraka.banking.entity.ParameterEntity;
import com.baraka.banking.map.ParameterMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ParameterMapImpl implements ParameterMap {

    @Override
    public ParameterBo toBo(ParameterEntity entity) {
        ParameterBo bo = new ParameterBo();
        BeanUtils.copyProperties(entity, bo);
        return bo;
    }
}
