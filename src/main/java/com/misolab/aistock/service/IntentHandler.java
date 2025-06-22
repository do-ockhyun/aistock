package com.misolab.aistock.service;

import com.misolab.aistock.model.ParsedQuery;

public interface IntentHandler {
    boolean canHandle(String intent);
    Object handle(ParsedQuery query);
} 