package com.Name_Flow.Name_Flow_Server.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AIServiceImple implements AIService {

    private final RestTemplate restTemplate ;

    @Value("${geminiSecretKey}")
    private String geminiKey ;

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/" +
            "v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";


    @Override
    public ObjectNode getResponse(String prompt) {
        String apiURL = String.format(API_URL_TEMPLATE, geminiKey);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", prompt);
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));

        String requestBody ;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        }catch (Exception e){
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<JsonNode> response = restTemplate.exchange(apiURL, HttpMethod.POST, request, JsonNode.class);

        if (response.getStatusCode() == HttpStatus.OK){
            ObjectNode obj = (ObjectNode) response.getBody();
            return obj;
        } else {
            throw new RuntimeException("Failed to get response body");
        }
    }

    @Override
    public String extractText(ObjectNode response) {

        JsonNode candidatesNode = response.path("candidates");
        if (candidatesNode.isArray() && candidatesNode.size() > 0) {
            JsonNode contentNode = candidatesNode.get(0).path("content");
            JsonNode partsNode = contentNode.path("parts");
            if (partsNode.isArray() && partsNode.size() > 0) {
                return partsNode.get(0).path("text").asText();
            }
        }
        throw new RuntimeException("Failed to extract text from response");
    }
}
