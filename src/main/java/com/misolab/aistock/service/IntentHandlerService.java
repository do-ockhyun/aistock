package com.misolab.aistock.service;

import com.misolab.aistock.model.ParsedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IntentHandlerService {

    private final List<IntentHandler> handlers;

    public Object processIntent(ParsedQuery query) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(query.getIntent()))
                .findFirst()
                .map(handler -> handler.handle(query))
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported intent: " + query.getIntent()));
    }
} 