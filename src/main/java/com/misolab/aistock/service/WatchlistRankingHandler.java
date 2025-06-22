package com.misolab.aistock.service;

import com.misolab.aistock.model.IntentResponse;
import com.misolab.aistock.model.ParsedQuery;
import com.misolab.aistock.model.WatchlistItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WatchlistRankingHandler implements IntentHandler {
    private final StockDataClient stockDataClient;

    @Override
    public boolean canHandle(String intent) {
        return "watchlist_ranking".equalsIgnoreCase(intent);
    }

    @Override
    public IntentResponse handle(ParsedQuery parsedQuery) {
        String rankingType = parsedQuery.getRankingType();
        if (rankingType == null) {
            rankingType = "top_gainers"; // 기본값으로 상승률 순위 설정
        }

        List<WatchlistItem> watchlist = stockDataClient.getWatchlistRanking(rankingType);

        return new IntentResponse(
                "watchlist_ranking",
                Map.of(
                        "watchlist", watchlist,
                        "ranking_type", rankingType
                )
        );
    }
} 