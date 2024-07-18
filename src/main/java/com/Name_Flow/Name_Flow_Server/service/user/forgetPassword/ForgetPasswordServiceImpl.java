package com.Name_Flow.Name_Flow_Server.service.user.forgetPassword;

import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.dto.VerifyResetPasswordDTO;
import com.Name_Flow.Name_Flow_Server.emailsender.EmailService;
import com.Name_Flow.Name_Flow_Server.emailsender.EmailTemplateName;
import com.Name_Flow.Name_Flow_Server.entity.UserAuthentication;
import com.Name_Flow.Name_Flow_Server.entity.VerificationData;
import com.Name_Flow.Name_Flow_Server.repository.UserAuthenticationRepository;
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
public class ForgetPasswordServiceImpl implements ForgetPasswordService{

    private final UserAuthenticationRepository userAuthenticationRepository;
    private final EmailService emailService;
    private final VerificationDataRepository verificationDataRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO forgetPasswordMailSend(String email) throws MessagingException {
        if(!userAuthenticationRepository.existsByEmail(email)) {
            return ResponseDTO.builder()
                    .status(false)
                    .message("Requested User Not Found")
                    .build();
        }
        String generatedActivationCode = generateActivationCode();
        VerificationData forgetPassVerify = VerificationData.builder()
                .email(email)
                .activationCode(generatedActivationCode)
                .createdDate(LocalDateTime.now())
                .expiresDate(LocalDateTime.now().plusMinutes(10))
                .build();
        verificationDataRepository.save(forgetPassVerify);
        emailService.sendEmail(
                email,
                "",
                "",
                "",
                EmailTemplateName.FORGET_PASSWORD_EMAIL,
                generatedActivationCode,
                "Forget Password Email Verify"
        );
        return ResponseDTO.builder()
                .status(true)
                .message("Forget Email Verification Sent Successfully")
                .build();
    }

    @Override
    public ResponseDTO verifyAndResetPassword(VerifyResetPasswordDTO verifyResetPasswordDTO) throws MessagingException {
        if(!verificationDataRepository.existsByEmail(verifyResetPasswordDTO.getEmail())){
            return ResponseDTO.builder()
                    .status(false)
                    .message("User Not registered there Email")
                    .build();
        }
        if(!verificationDataRepository.existsByActivationCode(verifyResetPasswordDTO.getActivationCode())){
            return ResponseDTO.builder()
                    .status(false)
                    .message("Invalid Activation code")
                    .build();
        }
        VerificationData verificationData=verificationDataRepository.findByActivationCode(
                verifyResetPasswordDTO.getActivationCode()
        );
        if(!Objects.equals(verificationData.getEmail(), verifyResetPasswordDTO.getEmail())){
            return ResponseDTO.builder()
                    .status(false)
                    .message("Activation Code is Not Match with your Email")
                    .build();
        }
        if(LocalDateTime.now().isAfter(verificationData.getExpiresDate())){
            String generatedActivationCode = generateActivationCode();
            emailService.sendEmail(verifyResetPasswordDTO.getEmail(),
                    "",
                    "",
                    "",
                    EmailTemplateName.FORGET_PASSWORD_EMAIL,
                    generatedActivationCode,
                    "Forget Password Email Verify");
            verificationData.setActivationCode(generatedActivationCode);
            verificationData.setCreatedDate(LocalDateTime.now());
            verificationData.setExpiresDate(LocalDateTime.now().plusMinutes(10));
            verificationDataRepository.save(verificationData);
            return ResponseDTO.builder()
                    .status(false)
                    .message("Activation Code has been Expired , New Activation Code sent Successfully")
                    .build();
        }
        UserAuthentication resetPasswordUser = userAuthenticationRepository.findByEmail(
                verifyResetPasswordDTO.getEmail()
        );
        resetPasswordUser.setPassword(passwordEncoder.encode(verifyResetPasswordDTO.getNewPassword()));
        userAuthenticationRepository.save(resetPasswordUser);
        return ResponseDTO.builder()
                .status(true)
                .message("Activation Code for Forget Password Request has been Verified And Password Updated Successfully")
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
