package com.misolab.aistock.api;

import com.misolab.aistock.model.ApiResponse;
import com.misolab.aistock.model.IntentInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/intents")
public class IntentController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<IntentInfo>>> getIntents() {
        List<IntentInfo> intents = Arrays.asList(
                new IntentInfo("stock_price_inquiry", "개별 종목 주가 조회",
                        Arrays.asList("삼성전자 주가 알려줘", "SK하이닉스 현재가는?")),
                new IntentInfo("market_index_analysis", "시장 지수 분석",
                        Arrays.asList("코스피 어때?", "오늘 지수 왜 떨어졌어?")),
                new IntentInfo("watchlist_ranking", "관심 종목 랭킹 조회",
                        Arrays.asList("내 관심종목 중 오른 것만 보여줘", "오늘 가장 많이 오른 관심종목은?")),
                new IntentInfo("portfolio_analysis", "포트폴리오 분석",
                        Arrays.asList("내 포트폴리오에서 가장 손실이 큰 종목은?", "수익률 제일 좋은 종목 알려줘")),
                new IntentInfo("general_inquiry", "일반 질의응답",
                        Arrays.asList("워렌 버핏 명언 알려줘", "주식 시장은 언제 열려?"))
        );

        return ResponseEntity.ok(ApiResponse.<List<IntentInfo>>builder()
                .success(true)
                .data(intents)
                .build());
    }
} 