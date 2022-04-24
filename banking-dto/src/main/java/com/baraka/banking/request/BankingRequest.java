package com.baraka.banking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NotNull(message = "Invalid request")
public class BankingRequest {

    @NotNull(message = "Invalid request payload")
    @NotEmpty(message = "Invalid request payload")
    private Map<String, Object> request;
}
