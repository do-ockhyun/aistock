package com.misolab.aistock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MarketIndex(
        String indexName,
        BigDecimal value,
        BigDecimal change,
        BigDecimal changeRate,
        LocalDateTime lastUpdated
) {
} 