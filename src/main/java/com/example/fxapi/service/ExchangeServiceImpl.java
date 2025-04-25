package com.example.fxapi.service;

import com.example.fxapi.client.ExchangeRateClient;
import com.example.fxapi.config.ExchangeApiProperties;
import com.example.fxapi.dto.ConversionTransactionDTO;
import com.example.fxapi.entity.ConversionTransaction;
import com.example.fxapi.mapper.ConversionTransactionMapper;
import com.example.fxapi.repository.ConversionTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor // Injects final fields via constructor automatically (Lombok)
public class ExchangeServiceImpl implements ExchangeService{

    private final ExchangeApiProperties apiProperties;

    private final ExchangeRateClient exchangeRateClient;

    private final ConversionTransactionRepository repository;

    private final ConversionTransactionMapper mapper;

    /**
     * Retrieves the latest exchange rate from an external API.
     * @param from The source currency (e.g., "USD").
     * @param to The target currency (e.g., "EUR").
     * @return Current exchange rate as BigDecimal.
     */
    public BigDecimal getExchangeRate(String from, String to) {
        String accessKey = apiProperties.getKey();
        Map<String, Object> response = exchangeRateClient.getRates(accessKey,from, to);
        System.out.println("Raw response: " + response); // 👈 Add this line for debugging

        Map<String, Object> rates = (Map<String, Object>) response.get("rates");

        if (rates == null || !rates.containsKey(to)) {
            throw new IllegalArgumentException("Exchange rate not available for: " + to);
        }
        return new BigDecimal(rates.get(to).toString());
    }

    /**
     * Converts a currency amount using the latest exchange rate.
     * Saves the transaction in the database.
     * @param from Source currency code.
     * @param to Target currency code.
     * @param amount Amount to convert.
     * @return DTO representing the saved conversion transaction.
     */
    public ConversionTransactionDTO convert(String from, String to, BigDecimal amount) {
        BigDecimal rate = getExchangeRate(from, to);
        BigDecimal convertedAmount = amount.multiply(rate);

        // Create transaction entity
        ConversionTransaction transaction = new ConversionTransaction(
                from,
                to,
                amount,
                convertedAmount,
                rate
        );

        // Save to DB and return mapped DTO
        ConversionTransaction saved = repository.saveAndFlush(transaction);
        return mapper.toDto(saved);
    }

    public List<ConversionTransactionDTO> getConversionHistory(UUID transactionId, LocalDate date) {
        // Lookup by transaction ID
        if (transactionId != null) {
            return repository.findById(transactionId)
                    .map(mapper::toDto)
                    .map(List::of)
                    .orElse(List.of());
        }

        if (date != null) {
            LocalDateTime from = date.atStartOfDay();
            LocalDateTime to = date.plusDays(1).atStartOfDay();

            return repository.findAllByTimestampBetween(from ,to).stream()
                    .map(mapper::toDto)
                    .toList();
        }

        // No valid filter provided
        throw new IllegalArgumentException("Either transactionId or date must be provided.");
    }



}
