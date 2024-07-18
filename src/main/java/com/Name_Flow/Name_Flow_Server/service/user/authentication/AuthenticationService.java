package com.Name_Flow.Name_Flow_Server.service.user.authentication;

import com.Name_Flow.Name_Flow_Server.dto.AuthenticationRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.AuthenticationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO);

}
