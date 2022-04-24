package com.baraka.banking.repository;

import com.baraka.banking.bo.TransactionBo;

public interface TransactionRepository {

    TransactionBo save(TransactionBo transactionBo);
}
