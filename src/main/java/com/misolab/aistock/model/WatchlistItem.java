package com.misolab.aistock.model;

public record WatchlistItem(
        int rank,
        String stockName,
        String stockCode,
        long currentPrice,
        double changeRate
) {
} 