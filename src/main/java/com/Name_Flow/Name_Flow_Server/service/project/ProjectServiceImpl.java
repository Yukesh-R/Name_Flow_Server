package com.Name_Flow.Name_Flow_Server.service.project;

import com.Name_Flow.Name_Flow_Server.dto.ProjectCreateRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.entity.ProjectData;
import com.Name_Flow.Name_Flow_Server.entity.UserAccessRelationData;
import com.Name_Flow.Name_Flow_Server.repository.ProjectDataRepository;
import com.Name_Flow.Name_Flow_Server.repository.UserAccessRelationDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDataRepository projectDataRepository;
    private final UserAccessRelationDataRepository userAccessRelationDataRepository;

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

    @Override
    public List<ProjectData> getOwnProjects(Long user_id) {
        return projectDataRepository.findAllByUserId(user_id);
    }

    @Override
    public List<ProjectData> getAccessProject(Long user_id) {
        List<UserAccessRelationData> accessRelationData=userAccessRelationDataRepository.findAllByUserId(user_id);
        List<ProjectData> accessProject = new ArrayList<>();
        for(UserAccessRelationData accessData : accessRelationData){
            List<ProjectData> temp = projectDataRepository.findAllById(
                    Collections.singleton(accessData.getAccessProjectId())
            );
            accessProject.addAll(temp);
        }
        return accessProject;
    }
}
