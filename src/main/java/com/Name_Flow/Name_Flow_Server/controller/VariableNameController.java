package com.Name_Flow.Name_Flow_Server.controller;

import com.Name_Flow.Name_Flow_Server.dto.*;
import com.Name_Flow.Name_Flow_Server.entity.VariableNameData;
import com.Name_Flow.Name_Flow_Server.service.variableSuggest.VariableSuggestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/name-flow")
@RequiredArgsConstructor
public class VariableNameController {

    private final VariableSuggestService variableSuggestService;

    @PostMapping("/create-variable-name-ai")
    public ResponseEntity<ResponseDTO> variableSuggester(@RequestBody CreateVariableNameRequestDTO variableSuggestRequestDTO) {
        return variableSuggestService.variableSuggester(variableSuggestRequestDTO);
    }

    @PostMapping("/create-variable-name-manual")
    public ResponseEntity<ResponseDTO> variableSuggester(@RequestBody CreateVariableNameManualRequestDTO createVariableNameManualRequestDTO) {
        return variableSuggestService.createVariableNameManual(createVariableNameManualRequestDTO);
    }

    @PostMapping("/get-variable-name")
    public ResponseEntity<List<VariableNameData>> getVariableName(@RequestBody GetVariableRequestDTO getVariableRequestDTO) {
        return variableSuggestService.getVariableName(getVariableRequestDTO);
    }

    @PostMapping("/update-variable")
    public ResponseEntity<ResponseDTO> updateVariable(@RequestBody UpdateVariableDTO updateVariableDTO) {
        return variableSuggestService.updateVariable(updateVariableDTO);
    }

    @PostMapping("/delete-variable")
    public ResponseEntity<ResponseDTO> deleteVariable(@RequestBody DeleteVariableDTO deleteVariableDTO) {
        System.out.println(deleteVariableDTO);
        return variableSuggestService.deleteVariable(deleteVariableDTO);
    }

}
