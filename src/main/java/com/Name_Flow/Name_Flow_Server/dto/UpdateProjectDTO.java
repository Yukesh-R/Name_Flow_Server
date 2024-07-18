package com.Name_Flow.Name_Flow_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProjectDTO {
    private Long userId;
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private String techStackDescription;
    private String projectContributors;
}
