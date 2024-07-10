package com.Name_Flow.Name_Flow_Server.service.registration;

import com.Name_Flow.Name_Flow_Server.dto.RegistrationRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.emailsender.EmailService;
import com.Name_Flow.Name_Flow_Server.emailsender.EmailTemplateName;
import com.Name_Flow.Name_Flow_Server.entity.RoleEnum;
import com.Name_Flow.Name_Flow_Server.entity.UserAuthentication;
import com.Name_Flow.Name_Flow_Server.entity.UserData;
import com.Name_Flow.Name_Flow_Server.entity.VerificationData;
import com.Name_Flow.Name_Flow_Server.repository.UserAuthenticationRepository;
import com.Name_Flow.Name_Flow_Server.repository.UserDataRepository;
import com.Name_Flow.Name_Flow_Server.repository.VerificationDataRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService{

    private final UserAuthenticationRepository userAuthenticationRepository;
    private final EmailService emailService;
    private final VerificationDataRepository verificationDataRepository;
    private final UserDataRepository userDataRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO registrationEmailValidation(String email) throws MessagingException {

        if(userAuthenticationRepository.existsByEmail(email)){
            return ResponseDTO.builder()
                    .status(false)
                    .message("User Already Exists")
                    .build();
        }
        String generatedActivationCode = generateActivationCode();
        emailService.sendEmail(email,
                "",
                EmailTemplateName.VERIFY_EMAIL,
                generatedActivationCode,
                "Name Flow - Email Verification");
        VerificationData verificationData= VerificationData.builder()
                .email(email)
                .activationCode(generatedActivationCode)
                .createdDate(LocalDateTime.now())
                .expiresDate(LocalDateTime.now().plusMinutes(10))
                .build();
        verificationDataRepository.save(verificationData);
        return ResponseDTO.builder()
                .status(true)
                .message("Verification Email has been Sent Successfully")
                .build();
    }

    @Override
    public ResponseDTO verifyActivationCode(RegistrationRequestDTO registrationRequestDTO) throws MessagingException {
        if(!verificationDataRepository.existsByEmail(registrationRequestDTO.getEmail())){
            return ResponseDTO.builder()
                    .status(false)
                    .message("User Not registered there Email")
                    .build();
        }
        if(!verificationDataRepository.existsByActivationCode(registrationRequestDTO.getActivationCode())){
            return ResponseDTO.builder()
                    .status(false)
                    .message("Invalid Activation code")
                    .build();
        }
        VerificationData verificationData=verificationDataRepository.findByActivationCode(
                registrationRequestDTO.getActivationCode()
        );
        if(!Objects.equals(verificationData.getEmail(), registrationRequestDTO.getEmail())){
            return ResponseDTO.builder()
                    .status(false)
                    .message("Activation Code is Not Match with your Email")
                    .build();
        }
        if(LocalDateTime.now().isAfter(verificationData.getExpiresDate())){
            String generatedActivationCode = generateActivationCode();
            emailService.sendEmail(registrationRequestDTO.getEmail(),
                    "",
                    EmailTemplateName.VERIFY_EMAIL,
                    generatedActivationCode,
                    "Name Flow - Email Verification");
            verificationData.setActivationCode(generatedActivationCode);
            verificationData.setCreatedDate(LocalDateTime.now());
            verificationData.setExpiresDate(LocalDateTime.now().plusMinutes(10));
            verificationDataRepository.save(verificationData);
            return ResponseDTO.builder()
                    .status(false)
                    .message("Activation Code has been Expired , New Activation Code sent Successfully")
                    .build();
        }
        UserData userData=UserData.builder()
                .firstName(registrationRequestDTO.getFirstName())
                .lastName(registrationRequestDTO.getLastName())
                .gender(registrationRequestDTO.getGender())
                .age(registrationRequestDTO.getAge())
                .mobileNumber(registrationRequestDTO.getMobileNumber())
                .build();
        userDataRepository.save(userData);
        RoleEnum responseRoleEnum = RoleEnum.valueOf(registrationRequestDTO.getRole());
        UserAuthentication userAuthentication=UserAuthentication.builder()
                .userId(userData.getId())
                .email(registrationRequestDTO.getEmail())
                .password(passwordEncoder.encode(registrationRequestDTO.getPassword()))
                .role(responseRoleEnum)
                .build();
        userAuthenticationRepository.save(userAuthentication);
        return ResponseDTO.builder()
                .status(true)
                .message("VActivation Code has been verified Successfully")
                .build();
    }

    private String generateActivationCode(){
        String characters = "0123456789";
        StringBuilder code = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i=0;i<6;i++){
            int randomIndex = secureRandom.nextInt(characters.length());
            code.append(characters.charAt(randomIndex));
        }
        return code.toString();
    }

}
