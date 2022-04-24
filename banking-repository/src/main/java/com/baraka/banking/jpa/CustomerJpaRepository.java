package com.baraka.banking.jpa;

import com.baraka.banking.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {

    CustomerEntity findByCustomerExternalId(String customerExternalId);
}
