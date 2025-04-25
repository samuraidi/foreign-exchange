package com.example.fxapi.service;

import com.example.fxapi.dto.ConversionTransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExchangeService {
    BigDecimal getExchangeRate(String from, String to);
    ConversionTransactionDTO convert(String from, String to, BigDecimal amount);
    List<ConversionTransactionDTO> getConversionHistory(UUID transactionId, LocalDate date);
}
