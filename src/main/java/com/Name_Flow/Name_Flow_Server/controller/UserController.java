package com.Name_Flow.Name_Flow_Server.controller;


import com.Name_Flow.Name_Flow_Server.ai.service.AIService;

import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameManualRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.CreateVariableNameRequestDTO;
import com.Name_Flow.Name_Flow_Server.service.variableSuggest.VariableSuggestService;

import com.Name_Flow.Name_Flow_Server.dto.RegistrationRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
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


    @Qualifier("AIService")
    private final AIService aiService;


    @PostMapping("/registration/emailValidation/{email}")
    public ResponseEntity<ResponseDTO> registrationEmailValidation(
            @PathVariable String email
    ) throws MessagingException {
        return ResponseEntity.ok(registrationService.registrationEmailValidation(email));
    }

    @PostMapping("/create-variable-name-ai")
    public ResponseEntity<ResponseDTO> variableSuggester(@RequestBody CreateVariableNameRequestDTO variableSuggestRequestDTO) {
        return variableSuggestService.variableSuggester(variableSuggestRequestDTO);
    }

    @PostMapping("/create-variable-name-manual")
    public ResponseEntity<ResponseDTO> variableSuggester(@RequestBody CreateVariableNameManualRequestDTO createVariableNameManualRequestDTO) {
        return variableSuggestService.createVariableNameManual(createVariableNameManualRequestDTO);
    }


    @PostMapping("registration/verification")
    public ResponseDTO verifyActivationCode(
            @RequestBody RegistrationRequestDTO registrationRequestDTO
            ) throws MessagingException {
        return registrationService.verifyActivationCode(registrationRequestDTO);

    }

}

