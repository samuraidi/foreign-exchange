package com.example.fxapi.repository;

import com.example.fxapi.entity.ConversionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConversionTransactionRepository extends JpaRepository<ConversionTransaction, UUID> {
    List<ConversionTransaction> findAllByTimestampBetween(LocalDateTime from, LocalDateTime to);

}
