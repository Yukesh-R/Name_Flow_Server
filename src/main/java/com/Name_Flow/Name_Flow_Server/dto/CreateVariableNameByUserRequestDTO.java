package com.Name_Flow.Name_Flow_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateVariableNameByUserRequestDTO {
    private String variableName;
    private String dataType ;
    private String variableUseCase;
    private String variableType;

}
