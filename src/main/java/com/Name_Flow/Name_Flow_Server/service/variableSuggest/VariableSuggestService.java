package com.Name_Flow.Name_Flow_Server.service.variableSuggest;

import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameManualRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VariableSuggestService {

    ResponseEntity<ResponseDTO> variableSuggester(CreateVariableNameRequestDTO variableSuggestRequestDTO);

    ResponseEntity<ResponseDTO> createVariableNameManual(CreateVariableNameManualRequestDTO variableSuggestRequestDTO);

    


}
