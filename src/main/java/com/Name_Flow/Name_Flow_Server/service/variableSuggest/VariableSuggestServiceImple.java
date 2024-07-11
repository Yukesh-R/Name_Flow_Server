package com.Name_Flow.Name_Flow_Server.service.variableSuggest;

import com.Name_Flow.Name_Flow_Server.ai.service.AIService;
import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameManualRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.entity.VariableNameData;
import com.Name_Flow.Name_Flow_Server.repository.VariableNameDataRepository;
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
    private final VariableNameDataRepository variableNameDataRepository;

    @Override
    public ResponseEntity<ResponseDTO> variableSuggester(CreateVariableNameRequestDTO variableSuggestRequestDTO) {

        ObjectNode responseFromAI = aiService.getResponse("Suggest me an single " + variableSuggestRequestDTO.getVariableType() + "name of datatype" + variableSuggestRequestDTO.getDataType() +
                "for storing " + variableSuggestRequestDTO.getDescription() + " in typescript without any explaination and the response should be only an single word ?");

        String suggestedVariableName = aiService.extractText(responseFromAI);

        CreateVariableNameManualRequestDTO createVariableNameManualRequestDTO = CreateVariableNameManualRequestDTO.builder()
                .userId(variableSuggestRequestDTO.getUserId())
                .projectId(variableSuggestRequestDTO.getProjectId())
                .variableName(suggestedVariableName)
                .variableType(variableSuggestRequestDTO.getVariableType())
                .dataType(variableSuggestRequestDTO.getDataType())
                .description(variableSuggestRequestDTO.getDescription()).build();

        try {
            return createVariableNameManual(createVariableNameManualRequestDTO);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null ;

    }

    @Override
    public ResponseEntity<ResponseDTO> createVariableNameManual(CreateVariableNameManualRequestDTO createVariableNameManualRequestDTO) {
        VariableNameData object = VariableNameData.builder()
                .userId(createVariableNameManualRequestDTO.getUserId())
                .projectId(createVariableNameManualRequestDTO.getProjectId())
                .variableName(createVariableNameManualRequestDTO.getVariableName())
                .dataType(createVariableNameManualRequestDTO.getDataType())
                .variableType(createVariableNameManualRequestDTO.getVariableType())
                .description(createVariableNameManualRequestDTO.getDescription()).build();

        try {
            variableNameDataRepository.save(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(true)
                .message("Variable Name Successfully Created").build());
    }
}
