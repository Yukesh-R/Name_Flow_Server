package com.Name_Flow.Name_Flow_Server.controller;

import com.Name_Flow.Name_Flow_Server.dto.CreateRelationShipDTO;
import com.Name_Flow.Name_Flow_Server.dto.ProjectCreateRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.RemoveProjectAccessDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.entity.ProjectData;
import com.Name_Flow.Name_Flow_Server.service.project.ProjectService;
import com.Name_Flow.Name_Flow_Server.service.userRelationShip.UserRelationshipService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/name-flow")
@RequiredArgsConstructor
public class ProjectController {

    private final UserRelationshipService userRelationshipService;
    private final ProjectService projectService;

    @PostMapping("create/new-project")
    public ResponseEntity<ResponseDTO> createProject(
            @RequestBody ProjectCreateRequestDTO projectCreateRequestDTO
    ){
        return ResponseEntity.ok(projectService.createProject(projectCreateRequestDTO));
    }

    @PostMapping("/create/project-relation")
    public ResponseEntity<ResponseDTO> createRelationship(
            @RequestBody CreateRelationShipDTO createRelationShipDTO
    ) throws MessagingException {
        return ResponseEntity.ok(userRelationshipService.createRelationship(createRelationShipDTO));
    }

    @PostMapping("/create/accept-access/{accepted_used_id}")
    public ResponseEntity<ResponseDTO> relationAccessAcceptance(
            @PathVariable Long accepted_used_id
    ){
        return ResponseEntity.ok(userRelationshipService.relationAccessAcceptance(accepted_used_id));
    }

    @PostMapping("/read/own-project/{user_id}")
    public ResponseEntity<List<ProjectData>> getOwnProjects(
            @PathVariable Long user_id
    ){
        return ResponseEntity.ok(projectService.getOwnProjects(user_id));
    }

    @PostMapping("/read/access-project/{user_id}")
    public ResponseEntity<List<ProjectData>> getAccessProject(
            @PathVariable Long user_id
    ){
        return ResponseEntity.ok(projectService.getAccessProject(user_id));
    }

    @PostMapping("/delete/project-access")
    public ResponseEntity<ResponseDTO> removeAccess(
            @RequestBody RemoveProjectAccessDTO removeProjectAccessDTO
    ) throws MessagingException {
        return ResponseEntity.ok(userRelationshipService.removeAccess(removeProjectAccessDTO));
    }

}
