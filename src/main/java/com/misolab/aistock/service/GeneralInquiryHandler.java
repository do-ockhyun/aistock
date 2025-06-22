package com.misolab.aistock.service;

import com.misolab.aistock.model.IntentResponse;
import com.misolab.aistock.model.ParsedQuery;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GeneralInquiryHandler implements IntentHandler {
    private static final String INTENT_NAME = "general_inquiry";

    @Override
    public boolean canHandle(String intent) {
        return INTENT_NAME.equals(intent);
    }

    @Override
    public IntentResponse handle(ParsedQuery parsedQuery) {
        // ResponseGeneratorService가 이 데이터를 사용하여 자연스러운 답변을 생성합니다.
        return new IntentResponse(INTENT_NAME, Map.of("user_query", parsedQuery.getOriginalQuery()));
    }
} 