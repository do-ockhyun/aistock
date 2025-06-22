package com.misolab.aistock.service;

import com.misolab.aistock.model.ParsedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StockPriceInquiryHandler implements IntentHandler {

    private static final Logger log = LoggerFactory.getLogger(StockPriceInquiryHandler.class);

    @Override
    public boolean canHandle(String intent) {
        return "stock_price_inquiry".equals(intent);
    }

    @Override
    public Object handle(ParsedQuery query) {
        log.info("주가 조회 로직 호출: {}", query);
        // TODO: Task 5에서 실제 주식 데이터를 조회하는 StockDataService를 호출해야 합니다.
        return query; // 지금은 파싱된 쿼리를 그대로 반환합니다.
    }
} 