package com.Name_Flow.Name_Flow_Server.service.project;

import com.Name_Flow.Name_Flow_Server.dto.ProjectCreateRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {

    ResponseDTO createProject(ProjectCreateRequestDTO projectCreateRequestDTO);

}
