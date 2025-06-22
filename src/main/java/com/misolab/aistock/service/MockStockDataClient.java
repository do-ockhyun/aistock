package com.misolab.aistock.service;

import com.misolab.aistock.model.MarketIndex;
import com.misolab.aistock.model.StockPrice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MockStockDataClient implements StockDataClient {

    private final Random random = new Random();

    @Override
    public StockPrice getCurrentPrice(String stockCode) {
        BigDecimal basePrice = new BigDecimal("100000");
        int priceFluctuation = random.nextInt(10000) - 5000;
        BigDecimal currentPrice = basePrice.add(new BigDecimal(priceFluctuation));

        int changeFluctuation = random.nextInt(4000) - 2000;
        BigDecimal change = new BigDecimal(changeFluctuation);

        BigDecimal previousPrice = currentPrice.subtract(change);
        BigDecimal changePercent = BigDecimal.ZERO;
        if (previousPrice.compareTo(BigDecimal.ZERO) != 0) {
            changePercent = change.divide(previousPrice, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
        }

        return new StockPrice(
                stockCode,
                "Mock Stock " + stockCode,
                currentPrice,
                change,
                changePercent.setScale(2, RoundingMode.HALF_UP),
                LocalDateTime.now()
        );
    }

    @Override
    public MarketIndex getMarketIndex(String indexType) {
        MarketIndex index = new MarketIndex();
        if ("KOSPI".equalsIgnoreCase(indexType)) {
            index.setName("코스피");
            index.setValue(2700.50);
            index.setChange(-25.20);
            index.setChangePercent(-0.92);
            index.setDescription("외국인 및 기관의 동반 매도세에 하락 마감했습니다.");
        } else if ("KOSDAQ".equalsIgnoreCase(indexType)) {
            index.setName("코스닥");
            index.setValue(850.15);
            index.setChange(5.10);
            index.setChangePercent(0.60);
            index.setDescription("개인의 순매수세에 힘입어 상승 마감했습니다.");
        }
        return index;
    }

    @Override
    public List<StockPrice> getWatchlistPrices(List<String> stockCodes) {
        // Return a list of mock StockPrice objects
        return stockCodes.stream()
                .map(this::getCurrentPrice)
                .collect(Collectors.toList());
    }
} 