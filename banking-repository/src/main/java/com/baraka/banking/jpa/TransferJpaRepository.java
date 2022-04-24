package com.baraka.banking.jpa;

import com.baraka.banking.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferJpaRepository extends JpaRepository<TransferEntity, Long> {
}
