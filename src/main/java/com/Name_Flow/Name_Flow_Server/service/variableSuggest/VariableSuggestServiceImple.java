package com.Name_Flow.Name_Flow_Server.service.variableSuggest;

import com.Name_Flow.Name_Flow_Server.ai.service.AIService;
import com.Name_Flow.Name_Flow_Server.dto.*;
import com.Name_Flow.Name_Flow_Server.entity.ProjectData;
import com.Name_Flow.Name_Flow_Server.entity.VariableNameData;
import com.Name_Flow.Name_Flow_Server.repository.ProjectDataRepository;
import com.Name_Flow.Name_Flow_Server.repository.VariableNameDataRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VariableSuggestServiceImple implements VariableSuggestService {

    @Qualifier("AIService")
    private final AIService aiService;
    private final VariableNameDataRepository variableNameDataRepository;
    private final ProjectDataRepository projectDataRepository;

    @Override
    public ResponseEntity<ResponseDTO> variableSuggester(CreateVariableNameRequestDTO variableSuggestRequestDTO) {

        ObjectNode responseFromAI = aiService.getResponse("I am creating an web application for that Suggest me an single " + variableSuggestRequestDTO.getVariableType() + "name of datatype" + variableSuggestRequestDTO.getDataType() +
                "for storing " + variableSuggestRequestDTO.getDescription() + " in typescript without any explaination and the response should be only an single word and dont't leave any response empty ?");

        System.out.println(responseFromAI);
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

    @Override
    public ResponseEntity<List<VariableNameData>> getVariableName(GetVariableRequestDTO getVariableRequestDTO) {

        ProjectData projectData = projectDataRepository.findById(getVariableRequestDTO.getProjectId()).orElseThrow();

        if (Objects.equals(projectData.getUserId(), getVariableRequestDTO.getUserId())) {

            return ResponseEntity.ok(variableNameDataRepository.findByProjectId(getVariableRequestDTO.getProjectId()));
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> updateVariable(UpdateVariableDTO updateVariableDTO) {

        VariableNameData variableNameData = variableNameDataRepository.findById(updateVariableDTO.getVariableId());

        if (Objects.equals(variableNameData.getUserId(), updateVariableDTO.getUserId())
                && Objects.equals(variableNameData.getProjectId(), updateVariableDTO.getProjectId()))
        {
            variableNameData.setVariableName(updateVariableDTO.getVariableName());
            variableNameData.setDataType(updateVariableDTO.getDataType());
            variableNameData.setDescription(updateVariableDTO.getDescription());
            variableNameData.setVariableType(updateVariableDTO.getVariableType());
            variableNameDataRepository.save(variableNameData);

            return ResponseEntity.ok(ResponseDTO.builder()
                    .status(true)
                    .message("Successfully Updated").build());
        }

        return ResponseEntity.ok(ResponseDTO.builder()
                .status(false)
                .message("Unable to Update").build());
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteVariable(DeleteVariableDTO deleteVariableDTO) {

        VariableNameData variableNameData = variableNameDataRepository.findById(deleteVariableDTO.getVariableId());
        System.out.println(variableNameData+"from service");
        if (variableNameData.getUserId().equals(deleteVariableDTO.getUserId())
                && Objects.equals(variableNameData.getProjectId(), deleteVariableDTO.getProjectId()) )
        {
            variableNameDataRepository.delete(variableNameData);
            return ResponseEntity.ok(ResponseDTO.builder()
                    .status(true)
                    .message("Successfully Deleted").build());
        }

        return ResponseEntity.ok(ResponseDTO.builder()
                .status(false)
                .message("Unable to Delete").build());
    }
}
