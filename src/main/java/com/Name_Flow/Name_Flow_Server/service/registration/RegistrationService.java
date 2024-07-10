package com.Name_Flow.Name_Flow_Server.service.registration;

import com.Name_Flow.Name_Flow_Server.dto.RegistrationRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

    ResponseDTO registrationEmailValidation(String email) throws MessagingException;

    ResponseDTO verifyActivationCode(RegistrationRequestDTO registrationRequestDTO) throws MessagingException;

}
