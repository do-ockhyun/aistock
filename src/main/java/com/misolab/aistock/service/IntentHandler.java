package com.misolab.aistock.service;

import com.misolab.aistock.model.IntentResponse;
import com.misolab.aistock.model.ParsedQuery;

public interface IntentHandler {
    boolean canHandle(String intent);

    IntentResponse handle(ParsedQuery query);
} 