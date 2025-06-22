package com.misolab.aistock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class IntentInfo {
    private String intent;
    private String description;
    private List<String> examples;
} 