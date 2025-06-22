package com.misolab.aistock.service;

import com.misolab.aistock.model.IntentResponse;
import com.misolab.aistock.model.MarketIndex;
import com.misolab.aistock.model.ParsedQuery;
import org.springframework.stereotype.Service;

@Service
public class MarketIndexAnalysisHandler implements IntentHandler {
    private final StockDataClient stockDataClient;
    private static final String INTENT_NAME = "market_index_analysis";

    public MarketIndexAnalysisHandler(StockDataClient stockDataClient) {
        this.stockDataClient = stockDataClient;
    }

    @Override
    public boolean canHandle(String intent) {
        return INTENT_NAME.equals(intent);
    }

    @Override
    public IntentResponse handle(ParsedQuery parsedQuery) {
        String indexType = parsedQuery.getIndexType();
        if (indexType == null || indexType.isBlank()) {
            indexType = "KOSPI"; // 기본값 설정
        }
        MarketIndex marketIndex = stockDataClient.getMarketIndex(indexType);

        return new IntentResponse(INTENT_NAME, marketIndex);
    }
} 