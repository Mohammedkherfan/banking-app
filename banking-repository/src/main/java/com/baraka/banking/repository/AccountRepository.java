package com.baraka.banking.repository;

import com.baraka.banking.bo.AccountBo;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    AccountBo save(AccountBo accountBo);

    Boolean existsByAccountNumber(String accountNumber);

    Optional<AccountBo> findByAccountNumber(String accountNumber);

    List<AccountBo> findAll();
}
