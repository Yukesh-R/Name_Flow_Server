package com.Name_Flow.Name_Flow_Server.service.user.forgetPassword;

import com.Name_Flow.Name_Flow_Server.dto.ResponseDTO;
import com.Name_Flow.Name_Flow_Server.dto.VerifyResetPasswordDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface ForgetPasswordService {

    ResponseDTO forgetPasswordMailSend(String email) throws MessagingException;

    ResponseDTO verifyAndResetPassword(VerifyResetPasswordDTO verifyResetPasswordDTO) throws MessagingException;

}
