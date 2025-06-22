package com.misolab.aistock.api;

import com.misolab.aistock.model.ParsedQuery;
import com.misolab.aistock.service.AIParserService;
import com.misolab.aistock.service.IntentHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test/parser")
@RequiredArgsConstructor
public class AIParserController {

    private final AIParserService aiParserService;
    private final IntentHandlerService intentHandlerService;

    @PostMapping
    public Object parseQuery(@RequestBody Map<String, String> payload) {
        String userQuery = payload.get("query");
        if (userQuery == null || userQuery.isBlank()) {
            throw new IllegalArgumentException("Query cannot be empty");
        }
        ParsedQuery parsedQuery = aiParserService.parseQuery(userQuery);
        return intentHandlerService.processIntent(parsedQuery);
    }
} 