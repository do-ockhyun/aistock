package com.misolab.aistock.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatQueryRequest {
    private String query;
    private String userId;
} 