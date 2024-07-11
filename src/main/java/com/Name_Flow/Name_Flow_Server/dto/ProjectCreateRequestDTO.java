package com.Name_Flow.Name_Flow_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProjectCreateRequestDTO {

    private Long userId;

    private String projectName;

    private String projectDescription;

    private String techStackDescription;

    private String projectContributors;

}
