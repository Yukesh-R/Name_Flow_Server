package com.Name_Flow.Name_Flow_Server.service.variableSuggest;

import com.Name_Flow.Name_Flow_Server.ai.service.AIService;
import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameResponseDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VariableSuggestServiceImple implements VariableSuggestService {

    @Qualifier("AIService")
    private final AIService aiService;

    @Override
    public ResponseEntity<CreateVariableNameResponseDTO> variableSuggester(CreateVariableNameRequestDTO variableSuggestRequestDTO) {

        ObjectNode responseFromAI = aiService.getResponse("Suggest me an single " + variableSuggestRequestDTO.getVariableType() + "name of datatype" + variableSuggestRequestDTO.getDataType() +
                "for storing " + variableSuggestRequestDTO.getVariableUseCase() + " in typescript without any explaination and the response should be only an single word ?");

        String suggestVariableName = aiService.extractText(responseFromAI);
        CreateVariableNameResponseDTO obj = CreateVariableNameResponseDTO.builder()
                .variableName(suggestVariableName)
                .variableType(variableSuggestRequestDTO.getVariableType())
                .dataType(variableSuggestRequestDTO.getDataType()).build();
        return ResponseEntity.ok(obj) ;
    }
}
