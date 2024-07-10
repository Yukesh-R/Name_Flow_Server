package com.Name_Flow.Name_Flow_Server.service.variableSuggest;

import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VariableSuggestService {

    ResponseEntity<CreateVariableNameResponseDTO> variableSuggester(CreateVariableNameRequestDTO variableSuggestRequestDTO);




}
