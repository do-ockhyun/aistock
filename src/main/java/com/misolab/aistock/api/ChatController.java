package com.misolab.aistock.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misolab.aistock.model.*;
import com.misolab.aistock.service.AIParserService;
import com.misolab.aistock.service.IntentHandlerService;
import com.misolab.aistock.service.ResponseGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final AIParserService parserService;
    private final IntentHandlerService intentHandlerService;
    private final ResponseGeneratorService responseGeneratorService;
    private final ObjectMapper objectMapper;

    @PostMapping("/query")
    public ResponseEntity<ApiResponse<ChatQueryResponse>> query(@RequestBody ChatQueryRequest request) {
        long startTime = System.currentTimeMillis();

        try {
            // 1. AI 파싱
            ParsedQuery parsedQuery = parserService.parseQuery(request.getQuery());

            // 2. Intent 처리
            IntentResponse intentResponse = intentHandlerService.processIntent(parsedQuery);

            // 3. 응답 생성
            String finalResponse = responseGeneratorService.generateResponse(intentResponse, parsedQuery.getIntent());
            
            long responseTime = System.currentTimeMillis() - startTime;

            // ParsedQuery를 Map으로 변환
            @SuppressWarnings("unchecked")
            Map<String, Object> entities = objectMapper.convertValue(parsedQuery, Map.class);
            entities.remove("originalQuery"); // 응답에 불필요한 필드 제거
            entities.remove("intent");

            // 4. 응답 반환
            return ResponseEntity.ok(ApiResponse.<ChatQueryResponse>builder()
                    .success(true)
                    .data(ChatQueryResponse.builder()
                            .response(finalResponse)
                            .intent(parsedQuery.getIntent())
                            .entities(entities)
                            .disclaimer("본 정보는 투자 참고용이며, 실시간 정보와 차이가 있을 수 있습니다.")
                            .build())
                    .responseTime(responseTime)
                    .build());
        } catch (Exception e) {
            long responseTime = System.currentTimeMillis() - startTime;
            // TODO: 에러 로그 저장
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<ChatQueryResponse>builder()
                            .success(false)
                            .error(e.getMessage())
                            .responseTime(responseTime)
                            .build());
        }
    }
} 