package com.baraka.banking.repository;

import com.baraka.banking.bo.ParameterBo;

import java.util.List;

public interface ParameterRepository {

    List<ParameterBo> findAllByOperation(String operation);
}
