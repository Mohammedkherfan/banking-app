package com.baraka.banking.bo;

import com.baraka.banking.enums.ParameterType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParameterBo {

    private Long id;
    private String operation;
    private String parameter;
    private ParameterType type;
    private Boolean required;
}
