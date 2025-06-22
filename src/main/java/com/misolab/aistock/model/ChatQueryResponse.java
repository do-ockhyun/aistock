package com.misolab.aistock.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ChatQueryResponse {
    private String response;
    private String intent;
    private Map<String, Object> entities;
    private String disclaimer;
} 