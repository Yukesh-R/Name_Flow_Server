package com.Name_Flow.Name_Flow_Server.controller;

import com.Name_Flow.Name_Flow_Server.dto.CreateRelationShipDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.service.userRelationShip.UserRelationshipService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/name-flow")
@RequiredArgsConstructor
public class ProjectController {

    private final UserRelationshipService userRelationshipService;

    @PostMapping("/create/project-relation")
    public ResponseEntity<ResponseDTO> createRelationship(
            @RequestBody CreateRelationShipDTO createRelationShipDTO
    ) throws MessagingException {
        return ResponseEntity.ok(userRelationshipService.createRelationship(createRelationShipDTO));
    }

}
