package com.misolab.aistock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StockPrice(
        String stockCode,
        String stockName,
        BigDecimal price,
        BigDecimal change,
        BigDecimal changeRate,
        LocalDateTime lastUpdated
) {
} 