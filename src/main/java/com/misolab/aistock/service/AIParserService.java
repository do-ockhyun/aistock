package com.misolab.aistock.service;

import com.misolab.aistock.model.ParsedQuery;

public interface AIParserService {
    ParsedQuery parseQuery(String userQuery);
} 