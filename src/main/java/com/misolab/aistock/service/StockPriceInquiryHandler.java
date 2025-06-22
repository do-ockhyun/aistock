package com.misolab.aistock.service;

import com.misolab.aistock.model.IntentResponse;
import com.misolab.aistock.model.ParsedQuery;
import com.misolab.aistock.model.StockPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StockPriceInquiryHandler implements IntentHandler {

    private static final Logger log = LoggerFactory.getLogger(StockPriceInquiryHandler.class);
    private final StockDataClient stockDataClient;

    public StockPriceInquiryHandler(StockDataClient stockDataClient) {
        this.stockDataClient = stockDataClient;
    }

    @Override
    public boolean canHandle(String intent) {
        return "stock_price_inquiry".equals(intent);
    }

    @Override
    public IntentResponse handle(ParsedQuery query) {
        log.info("주가 조회 로직 호출: {}", query);
        StockPrice stockPrice = stockDataClient.getCurrentPrice(query.getStockCode());
        return new IntentResponse(query.getIntent(), stockPrice);
    }
} 