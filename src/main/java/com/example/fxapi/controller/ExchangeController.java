package com.example.fxapi.controller;

import com.example.fxapi.dto.ConversionTransactionDTO;
import com.example.fxapi.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor // Auto-generates constructor for final fields (ExchangeService)
@Tag(name = "Foreign Exchange API", description = "Endpoints for exchange rate, conversion and history tracking")
public class ExchangeController {

    private final ExchangeService exchangeService;

    /**
     * GET /rate
     * Retrieves the current exchange rate between two currencies.
     *
     * @param from Source currency code (e.g., USD)
     * @param to   Target currency code (e.g., EUR)
     * @return The exchange rate as BigDecimal
     */
    @GetMapping("/rate")
    @Operation(summary = "Get exchange rate", description = "Returns the current exchange rate from one currency to another.")
    public ResponseEntity<BigDecimal> getRate(
            @RequestParam String from,
            @RequestParam String to
            ) {
        BigDecimal rate = exchangeService.getExchangeRate(from, to);
        return ResponseEntity.ok(rate);
    }

    /**
     * POST /convert
     * Converts an amount from one currency to another and stores the transaction.
     *
     * @param from   Source currency code
     * @param to     Target currency code
     * @param amount Amount to convert
     * @return The saved transaction as a DTO
     */
    @PostMapping("/convert")
    @Operation(summary = "Convert currency", description = "Converts an amount from source to target currency and stores the transaction.")
    public ResponseEntity<ConversionTransactionDTO> convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam BigDecimal amount
    ) {
        ConversionTransactionDTO result = exchangeService.convert(from, to, amount);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /history
     * Retrieves conversion transaction history by:
     * - transactionId (specific record)
     * - date (all conversions on that date)
     * One of the two filters must be provided.
     *
     * @param transactionId Optional UUID of a specific transaction
     * @param date Optional LocalDate to fetch all conversions on that day
     * @return List of matching transactions
     */
    @GetMapping("/history")
    @Operation(summary = "Get conversion history", description = "Fetches currency conversion history by transaction ID or date.")
    public ResponseEntity<List<ConversionTransactionDTO>> getHistory(
            @RequestParam(required = false)
            @Parameter(description = "Transaction ID") UUID transactionId,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "Transaction date (YYYY-MM-DD")LocalDate date
            ) {
        List<ConversionTransactionDTO> history = exchangeService.getConversionHistory(transactionId,date);
        return ResponseEntity.ok(history);
    }
}
