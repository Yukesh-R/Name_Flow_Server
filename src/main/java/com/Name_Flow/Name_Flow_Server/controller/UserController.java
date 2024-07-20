package com.Name_Flow.Name_Flow_Server.controller;

import com.Name_Flow.Name_Flow_Server.dto.*;
import com.Name_Flow.Name_Flow_Server.service.user.authentication.AuthenticationService;
import com.Name_Flow.Name_Flow_Server.service.user.forgetPassword.ForgetPasswordService;
import com.Name_Flow.Name_Flow_Server.service.user.registration.RegistrationService;
import com.Name_Flow.Name_Flow_Server.service.user.update.UpdateService;
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
    private final UpdateService updateService;

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
        return ResponseEntity.ok(forgetPasswordService.forgetPasswordMailSend(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDTO> verifyAndResetPassword(
            @RequestBody VerifyResetPasswordDTO verifyResetPasswordDTO
    ) throws MessagingException {
        return ResponseEntity.ok(forgetPasswordService.verifyAndResetPassword(verifyResetPasswordDTO));
    }

    @PostMapping("/update-user")
    public ResponseEntity<ResponseDTO> updateUser(
            @RequestBody UpdateUserDetailsDTO updateUserDetailsDTO
    ) throws MessagingException {
        return ResponseEntity.ok(updateService.updateUser(updateUserDetailsDTO));
    }

    @GetMapping("/delete-user/{userId}")
    public ResponseEntity<ResponseDTO> deleteUser(
            @PathVariable Long userId
    ) throws MessagingException {
        return ResponseEntity.ok(updateService.deleteUser(userId));
    }

}

