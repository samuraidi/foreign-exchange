package com.example.fxapi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateConversionTransactionDTO {
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal amount;
}
