package com.Name_Flow.Name_Flow_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVariableNameResponseDTO {
    private String variableName;
    private String dataType;
    private String variableType;
}
