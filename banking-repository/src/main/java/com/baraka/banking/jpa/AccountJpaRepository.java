package com.baraka.banking.jpa;

import com.baraka.banking.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {

    Boolean existsByAccountNumber(String accountNumber);

    AccountEntity findByAccountNumber(String accountNumber);
}
