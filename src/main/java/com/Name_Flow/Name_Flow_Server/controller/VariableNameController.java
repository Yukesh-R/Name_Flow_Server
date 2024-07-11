package com.Name_Flow.Name_Flow_Server.controller;

import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameManualRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.service.variableSuggest.VariableSuggestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
