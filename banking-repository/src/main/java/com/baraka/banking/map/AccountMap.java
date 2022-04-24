package com.baraka.banking.map;

import com.baraka.banking.bo.AccountBo;
import com.baraka.banking.entity.AccountEntity;

public interface AccountMap {

    AccountBo toBo(AccountEntity entity);

    AccountEntity toEntity(AccountBo bo);
}
