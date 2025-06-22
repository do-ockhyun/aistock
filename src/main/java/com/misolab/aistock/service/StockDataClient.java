package com.misolab.aistock.service;

import com.misolab.aistock.model.MarketIndex;
import com.misolab.aistock.model.StockPrice;

import java.util.List;

public interface StockDataClient {
    StockPrice getCurrentPrice(String stockCode);
    MarketIndex getMarketIndex(String indexType);
    List<StockPrice> getWatchlistPrices(List<String> stockCodes);
} 