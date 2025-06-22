package com.misolab.aistock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misolab.aistock.model.IntentResponse;
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
public class LLMResponseGeneratorImpl implements ResponseGeneratorService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Value("classpath:prompt/response_generator.md")
    private Resource promptResource;

    private String systemPrompt;
    private String userPromptTemplate;

    public LLMResponseGeneratorImpl(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
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
    public String generateResponse(IntentResponse data, String intent) {
        try {
            String jsonData = objectMapper.writeValueAsString(data);
            String userPrompt = userPromptTemplate
                    .replace("{intent}", intent)
                    .replace("{json_data}", jsonData);

            return chatClient.prompt()
                    .system(systemPrompt)
                    .user(userPrompt)
                    .call()
                    .content();

        } catch (JsonProcessingException e) {
            // TODO: 정교한 예외 처리 필요
            throw new RuntimeException("Failed to serialize data for AI response generation", e);
        }
    }
} 