package com.misolab.aistock.service;

import com.misolab.aistock.model.IntentResponse;
import com.misolab.aistock.model.ParsedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IntentHandlerService {

    private final Map<String, IntentHandler> intentHandlers;

    public IntentHandlerService(List<IntentHandler> handlers) {
        this.intentHandlers = handlers.stream()
                .collect(Collectors.toMap(handler -> handler.getClass().getSimpleName().toLowerCase(), Function.identity()));
    }

    public IntentResponse processIntent(ParsedQuery parsedQuery) {
        for (IntentHandler handler : intentHandlers.values()) {
            if (handler.canHandle(parsedQuery.getIntent())) {
                return handler.handle(parsedQuery);
            }
        }
        // TODO: 처리할 핸들러가 없을 경우 예외 처리 또는 기본 응답 처리
        throw new UnsupportedOperationException("Unsupported intent: " + parsedQuery.getIntent());
    }
} 