package com.Name_Flow.Name_Flow_Server.controller;


import com.Name_Flow.Name_Flow_Server.ai.service.AIService;

import com.Name_Flow.Name_Flow_Server.dto.*;
import com.Name_Flow.Name_Flow_Server.service.authentication.AuthenticationService;
import com.Name_Flow.Name_Flow_Server.service.userRelationShip.UserRelationshipService;
import com.Name_Flow.Name_Flow_Server.service.variableSuggest.VariableSuggestService;

import com.Name_Flow.Name_Flow_Server.service.registration.RegistrationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/name-flow")
@RequiredArgsConstructor
public class UserController {

    private final RegistrationService registrationService;
    private final VariableSuggestService variableSuggestService;
    private final AuthenticationService authenticationService;
    private final UserRelationshipService userRelationshipService;

    @Qualifier("AIService")
    private final AIService aiService;

    @PostMapping("/registration/emailValidation/{email}")
    public ResponseEntity<ResponseDTO> registrationEmailValidation(
            @PathVariable String email
    ) throws MessagingException {
        return ResponseEntity.ok(registrationService.registrationEmailValidation(email));
    }

    @PostMapping("/get-variable-name")
    public ResponseEntity<CreateVariableNameResponseDTO> variableSuggester(@RequestBody CreateVariableNameRequestDTO variableSuggestRequestDTO) {
        return variableSuggestService.variableSuggester(variableSuggestRequestDTO);
    }

    @PostMapping("registration/verification")
    public ResponseDTO verifyActivationCode(
            @RequestBody RegistrationRequestDTO registrationRequestDTO
            ) throws MessagingException {
        return registrationService.verifyActivationCode(registrationRequestDTO);

    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO authenticationRequestDTO
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }

    @PostMapping("/create/project-relation")
    public ResponseEntity<ResponseDTO> createRelationship(
            @RequestBody CreateRelationShipDTO createRelationShipDTO
    ) throws MessagingException {
        return ResponseEntity.ok(userRelationshipService.createRelationship(createRelationShipDTO));
    }

}

