package com.baraka.banking.map;

import com.baraka.banking.bo.TransactionBo;
import com.baraka.banking.entity.TransactionEntity;

public interface TransactionMap {

    TransactionBo toBo(TransactionEntity entity);

    TransactionEntity toEntity(TransactionBo bo);
}
