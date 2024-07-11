package com.Name_Flow.Name_Flow_Server.service.project;

import com.Name_Flow.Name_Flow_Server.dto.ProjectCreateRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.entity.ProjectData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    ResponseDTO createProject(ProjectCreateRequestDTO projectCreateRequestDTO);

    List<ProjectData> getOwnProjects(Long user_id);

    List<ProjectData> getAccessProject(Long user_id);

}
