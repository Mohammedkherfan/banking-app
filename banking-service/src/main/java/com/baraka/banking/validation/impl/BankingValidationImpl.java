package com.baraka.banking.validation.impl;

import com.baraka.banking.bo.ParameterBo;
import com.baraka.banking.enums.ParameterType;
import com.baraka.banking.exception.BankingException;
import com.baraka.banking.repository.ParameterRepository;
import com.baraka.banking.request.BankingRequest;
import com.baraka.banking.util.BankingUtil;
import com.baraka.banking.validation.BankingValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankingValidationImpl implements BankingValidation {

    private ParameterRepository parameterRepository;

    public BankingValidationImpl(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    @Override
    public void validate(BankingRequest request) {
        String operation = BankingUtil.getOperation(request.getRequest());
        List<ParameterBo> parameters = parameterRepository.findAllByOperation(operation);
        if (parameters.isEmpty())
            throw new BankingException(String.format("Invalid operation %1s", operation));
        validateExtra(request.getRequest(), parameters);
        validateRequired(request.getRequest(), parameters);
        ValidateType(request.getRequest(), parameters);
    }

    private void validateExtra(Map<String, Object> request, List<ParameterBo> parameters) {
        List<String> requestParams = request.keySet().stream().collect(Collectors.toList());
        List<String> operationParams = parameters.stream().map(ParameterBo::getParameter).collect(Collectors.toList());
        requestParams.removeAll(operationParams);

        if (!requestParams.isEmpty())
            throw new BankingException(requestParams + " extra parameters");
    }

    private void ValidateType(Map<String, Object> request, List<ParameterBo> parameters) {
        List<String> params = new ArrayList<>();

        request.entrySet().forEach(parameter -> {
            if (!match(parameter.getValue(), getParameter(parameter.getKey(), parameters)))
                params.add(parameter.getKey());
        });

        if (!params.isEmpty())
            throw new BankingException(params + " invalid parameters format");
    }

    private ParameterType getParameter(String key, List<ParameterBo> parameters) {
        Optional<ParameterBo> parameterBo = parameters.stream().filter(param -> key.equals(param.getParameter())).findFirst();
        if (parameterBo.isPresent())
            return parameterBo.get().getType();
        else
            throw new BankingException("Invalid parameter " + key);
    }

    private Boolean match(Object value, ParameterType parameterType) {
        return parameterType.getPattern().matcher(String.valueOf(value)).matches();
    }

    private void validateRequired(Map<String, Object> request, List<ParameterBo> parameters) {
        List<String> params = new ArrayList<>();

        List<ParameterBo> requiredParameters = parameters.stream().filter(ParameterBo::getRequired).collect(Collectors.toList());
        requiredParameters.forEach(parameterBo -> {
            if (!request.containsKey(parameterBo.getParameter()))
                params.add(parameterBo.getParameter());
        });

        if (!params.isEmpty())
            throw new BankingException(params + " missing required parameter");
    }
}
