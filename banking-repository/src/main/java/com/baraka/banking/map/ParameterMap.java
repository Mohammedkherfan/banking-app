package com.baraka.banking.map;

import com.baraka.banking.bo.ParameterBo;
import com.baraka.banking.entity.ParameterEntity;

public interface ParameterMap {

    ParameterBo toBo(ParameterEntity entity);
}
