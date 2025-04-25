package com.example.fxapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ConversionTransactionDTO {
    private UUID id;
    private String sourceCurrency;
    private String targetCurrency;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private BigDecimal rate;
    private LocalDateTime timestamp;
}


