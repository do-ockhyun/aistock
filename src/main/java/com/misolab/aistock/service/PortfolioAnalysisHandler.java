package com.misolab.aistock.service;

import com.misolab.aistock.model.IntentResponse;
import com.misolab.aistock.model.ParsedQuery;
import com.misolab.aistock.model.PortfolioItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PortfolioAnalysisHandler implements IntentHandler {
    private final StockDataClient stockDataClient;

    @Override
    public boolean canHandle(String intent) {
        return "portfolio_analysis".equalsIgnoreCase(intent);
    }

    @Override
    public IntentResponse handle(ParsedQuery parsedQuery) {
        String analysisType = parsedQuery.getAnalysisType();
        if (analysisType == null) {
            // 기본값 또는 오류 처리
            analysisType = "profit_analysis"; // 예를 들어 수익 분석을 기본값으로 설정
        }

        List<PortfolioItem> portfolio = stockDataClient.getPortfolio(analysisType);

        return new IntentResponse(
                "portfolio_analysis",
                Map.of(
                        "portfolio", portfolio,
                        "analysis_type", analysisType
                )
        );
    }
} 