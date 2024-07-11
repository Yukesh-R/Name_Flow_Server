package com.Name_Flow.Name_Flow_Server.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteVariableDTO {
    private Long userId;
    private Long projectId;
    private Long variableId;
}
