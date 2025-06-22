package com.misolab.aistock.service;

import com.misolab.aistock.model.IntentResponse;

public interface ResponseGeneratorService {
    String generateResponse(IntentResponse data, String intent);
} 