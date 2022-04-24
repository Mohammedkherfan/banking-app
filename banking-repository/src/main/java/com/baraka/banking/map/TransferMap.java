package com.baraka.banking.map;

import com.baraka.banking.bo.TransferBo;
import com.baraka.banking.entity.TransferEntity;

public interface TransferMap {

    TransferBo toBo(TransferEntity entity);

    TransferEntity toEntity(TransferBo bo);
}
