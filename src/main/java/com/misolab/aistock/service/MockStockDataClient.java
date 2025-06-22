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
        BigDecimal baseIndex = new BigDecimal("3000");
        double indexFluctuation = (random.nextDouble() * 100) - 50;
        BigDecimal currentIndex = baseIndex.add(new BigDecimal(indexFluctuation)).setScale(2, RoundingMode.HALF_UP);

        double changeFluctuation = (random.nextDouble() * 60) - 30;
        BigDecimal change = new BigDecimal(changeFluctuation).setScale(2, RoundingMode.HALF_UP);

        BigDecimal previousIndex = currentIndex.subtract(change);
        BigDecimal changePercent = BigDecimal.ZERO;
        if (previousIndex.compareTo(BigDecimal.ZERO) != 0) {
            changePercent = change.divide(previousIndex, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
        }

        return new MarketIndex(
                indexType,
                currentIndex,
                change,
                changePercent.setScale(2, RoundingMode.HALF_UP),
                LocalDateTime.now()
        );
    }

    @Override
    public List<StockPrice> getWatchlistPrices(List<String> stockCodes) {
        // Return a list of mock StockPrice objects
        return stockCodes.stream()
                .map(this::getCurrentPrice)
                .collect(Collectors.toList());
    }
} 