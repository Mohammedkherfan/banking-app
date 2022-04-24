package com.baraka.banking.map;

import com.baraka.banking.bo.CustomerBo;
import com.baraka.banking.entity.CustomerEntity;

public interface CustomerMap {

    CustomerBo toBo(CustomerEntity entity);

    CustomerEntity toEntity(CustomerBo bo);
}
