package com.Name_Flow.Name_Flow_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateVariableDTO {
    private Long userId;
    private Long projectId;
    private Long variableId;
    private String variableName;
    private String dataType;
    private String description;
    private String variableType;

}
