package com.Name_Flow.Name_Flow_Server.ai.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public interface AIService {

     ObjectNode getResponse(String prompt);

     String extractText(ObjectNode response);
}
