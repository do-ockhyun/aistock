package com.misolab.aistock.service;

import com.misolab.aistock.model.MarketIndex;
import com.misolab.aistock.model.PortfolioItem;
import com.misolab.aistock.model.StockPrice;
import com.misolab.aistock.model.WatchlistItem;

import java.util.List;

public interface StockDataClient {
    StockPrice getCurrentPrice(String stockCode);
    MarketIndex getMarketIndex(String indexType);
    List<StockPrice> getWatchlistPrices(List<String> stockCodes);
    List<PortfolioItem> getPortfolio(String analysisType);
    List<MarketIndex> getMarketIndexes(List<String> indexTypes);
    List<WatchlistItem> getWatchlistRanking(String rankingType);
} 