package com.baraka.banking.repository;

import com.baraka.banking.bo.TransferBo;

public interface TransferRepository {

    TransferBo save(TransferBo transferBo);
}
