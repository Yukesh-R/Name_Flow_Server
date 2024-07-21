package com.Name_Flow.Name_Flow_Server.controller;

import com.Name_Flow.Name_Flow_Server.dto.*;
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

    @PostMapping("/create/new-project")
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

    @GetMapping("/access-providers/{user_id}")
    public ResponseEntity<List<AccessProviderDTO>> getAccessProviders(
            @PathVariable Long user_id
    ){
        return ResponseEntity.ok(userRelationshipService.getAccessProviders(user_id));
    }

    @PostMapping("/project-accept-access")
    public ResponseEntity<ResponseDTO> relationAccessAcceptance(
            @RequestBody AccessAcceptDTO accessAcceptDTO
    ){
        return ResponseEntity.ok(userRelationshipService.relationAccessAcceptance(accessAcceptDTO));
    }

    @GetMapping("/read/own-project/{user_id}")
    public ResponseEntity<List<ProjectData>> getOwnProjects(
            @PathVariable Long user_id
    ){
        return ResponseEntity.ok(projectService.getOwnProjects(user_id));
    }

    @GetMapping("/read/access-project/{user_id}")
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

    @PostMapping("/update-project")
    public ResponseEntity<ResponseDTO> updateProject(
            @RequestBody UpdateProjectDTO updateProjectDTO
    ) throws MessagingException {
        return ResponseEntity.ok(projectService.updateProject(updateProjectDTO));
    }

}
