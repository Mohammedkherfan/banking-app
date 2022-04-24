package com.baraka.banking.jpa;

import com.baraka.banking.entity.ParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterJpaRepository extends JpaRepository<ParameterEntity, Long> {

    List<ParameterEntity> findAllByOperation(String operation);
}
