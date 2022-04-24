package com.baraka.banking.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankingResponse<T> {

    private T response;
}
