package com.example.fxapi.mapper;

import com.example.fxapi.dto.ConversionTransactionDTO;
import com.example.fxapi.entity.ConversionTransaction;
import org.springframework.stereotype.Component;

@Component
public class ConversionTransactionMapper {

    public ConversionTransactionDTO toDto(ConversionTransaction entity) {
        ConversionTransactionDTO dto = new ConversionTransactionDTO();
        dto.setId(entity.getId());
        dto.setSourceCurrency(entity.getSourceCurrency());
        dto.setTargetCurrency(entity.getTargetCurrency());
        dto.setAmount(entity.getSourceAmount());
        dto.setConvertedAmount(entity.getTargetAmount());
        dto.setRate(entity.getRate());
        dto.setTimestamp(entity.getTimestamp());
        return dto;
    }

}
