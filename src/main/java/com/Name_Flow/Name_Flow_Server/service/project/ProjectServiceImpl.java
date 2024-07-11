package com.Name_Flow.Name_Flow_Server.service.project;

import com.Name_Flow.Name_Flow_Server.dto.ProjectCreateRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.entity.ProjectData;
import com.Name_Flow.Name_Flow_Server.repository.ProjectDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDataRepository projectDataRepository;

    @Override
    public ResponseDTO createProject(ProjectCreateRequestDTO projectCreateRequestDTO) {

        if(projectDataRepository.existsByProjectName(projectCreateRequestDTO.getProjectName())){
            return ResponseDTO.builder()
                    .status(false)
                    .message("Project Name Already Exists")
                    .build();
        }

        ProjectData newProject = ProjectData.builder()
                .userId(projectCreateRequestDTO.getUserId())
                .projectName(projectCreateRequestDTO.getProjectName())
                .projectContributors(projectCreateRequestDTO.getProjectDescription())
                .projectDescription(projectCreateRequestDTO.getProjectDescription())
                .techStackDescription(projectCreateRequestDTO.getTechStackDescription())
                .projectContributors(projectCreateRequestDTO.getProjectContributors())
                .build();

        projectDataRepository.save(newProject);

        return ResponseDTO.builder()
                .status(true)
                .message("Project Created Successfully")
                .build();
    }
}
