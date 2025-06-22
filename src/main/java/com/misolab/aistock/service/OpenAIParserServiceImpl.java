package com.misolab.aistock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misolab.aistock.model.ParsedQuery;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

@Service
public class OpenAIParserServiceImpl implements AIParserService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Value("classpath:prompt/system.md")
    private Resource promptResource;

    private String systemPrompt;
    private String userPromptTemplate;


    public OpenAIParserServiceImpl(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try (InputStreamReader reader = new InputStreamReader(promptResource.getInputStream(), StandardCharsets.UTF_8)) {
            String fileContent = FileCopyUtils.copyToString(reader);
            String[] parts = fileContent.split("---", 2);
            if (parts.length < 2) {
                throw new IllegalStateException("Prompt file is not structured correctly. Expected '---' separator.");
            }
            this.systemPrompt = parts[0];
            this.userPromptTemplate = parts[1];
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    @Override
    public ParsedQuery parseQuery(String userQuery) {
        String userPrompt = userPromptTemplate.replace("{user_query}", userQuery);

        String content = chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

        try {
            String jsonResponse = content.trim();
            if (jsonResponse.startsWith("```json")) {
                jsonResponse = jsonResponse.substring(7);
            }
            if (jsonResponse.endsWith("```")) {
                jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 3);
            }
            return objectMapper.readValue(jsonResponse.trim(), ParsedQuery.class);
        } catch (JsonProcessingException e) {
            // TODO: 정교한 예외 처리 필요
            throw new RuntimeException("Failed to parse AI response: " + content, e);
        }
    }
} 