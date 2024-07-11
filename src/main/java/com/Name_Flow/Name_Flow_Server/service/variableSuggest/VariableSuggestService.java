package com.Name_Flow.Name_Flow_Server.service.variableSuggest;

import com.Name_Flow.Name_Flow_Server.dto.*;
import com.Name_Flow.Name_Flow_Server.entity.VariableNameData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VariableSuggestService {

    ResponseEntity<ResponseDTO> variableSuggester(CreateVariableNameRequestDTO variableSuggestRequestDTO);

    ResponseEntity<ResponseDTO> createVariableNameManual(CreateVariableNameManualRequestDTO variableSuggestRequestDTO);

    ResponseEntity<List<VariableNameData>> getVariableName(GetVariableRequestDTO getVariableRequestDTO);

    ResponseEntity<ResponseDTO> updateVariable(UpdateVariableDTO updateVariableDTO);

    ResponseEntity<ResponseDTO> deleteVariable(DeleteVariableDTO deleteVariableDTO);

}
