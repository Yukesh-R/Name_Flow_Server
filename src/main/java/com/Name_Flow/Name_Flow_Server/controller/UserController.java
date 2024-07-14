package com.Name_Flow.Name_Flow_Server.controller;

import com.Name_Flow.Name_Flow_Server.dto.*;
import com.Name_Flow.Name_Flow_Server.service.authentication.AuthenticationService;
import com.Name_Flow.Name_Flow_Server.service.forgetPassword.ForgetPasswordService;
import com.Name_Flow.Name_Flow_Server.service.registration.RegistrationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/name-flow")
@RequiredArgsConstructor
public class UserController {

    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;
    private final ForgetPasswordService forgetPasswordService;

    @GetMapping("/registration/email-validation/{email}")
    public ResponseEntity<ResponseDTO> registrationEmailValidation(
            @PathVariable String email
    ) throws MessagingException {
        return ResponseEntity.ok(registrationService.registrationEmailValidation(email));
    }

    @PostMapping("/registration/verification")
    public ResponseEntity<ResponseDTO> verifyActivationCode(
            @RequestBody RegistrationRequestDTO registrationRequestDTO
            ) throws MessagingException {
        return ResponseEntity.ok(registrationService.verifyActivationCode(registrationRequestDTO));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO authenticationRequestDTO
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }

    @GetMapping("/forget-password/{email}")
    public ResponseEntity<ResponseDTO> forgetPasswordMailSend(
            @PathVariable String email
    ) throws MessagingException {
        System.out.println(email);
        return ResponseEntity.ok(forgetPasswordService.forgetPasswordMailSend(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> verifyAndResetPassword(
            @RequestBody VerifyResetPasswordDTO verifyResetPasswordDTO
    ) throws MessagingException {
        return ResponseEntity.ok(forgetPasswordService.verifyAndResetPassword(verifyResetPasswordDTO));
    }

}

