package com.Name_Flow.Name_Flow_Server.service.authentication;

import com.Name_Flow.Name_Flow_Server.dto.AuthenticationRequestDTO;
import com.Name_Flow.Name_Flow_Server.dto.AuthenticationResponseDTO;
import com.Name_Flow.Name_Flow_Server.entity.UserAuthentication;
import com.Name_Flow.Name_Flow_Server.entity.UserData;
import com.Name_Flow.Name_Flow_Server.repository.UserAuthenticationRepository;
import com.Name_Flow.Name_Flow_Server.repository.UserDataRepository;
import com.Name_Flow.Name_Flow_Server.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserAuthenticationRepository userAuthenticationRepository;
    private final UserDataRepository userDataRepository;

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.getEmail(),
                        authenticationRequestDTO.getPassword()
                )
        );

        UserAuthentication userAuthentication = userAuthenticationRepository.findByEmail(
                authenticationRequestDTO.getEmail()
        );

        UserData userData = userDataRepository.findById(userAuthentication.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        var claims = new HashMap<String, Object>();
        var user = (UserAuthentication)auth.getPrincipal();
        claims.put("role",user.getRole());
        String generatedJwtToken = jwtService.generateToken(claims,user);

        return AuthenticationResponseDTO.builder()
                .userId(userAuthentication.getUserId())
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .gender(userData.getGender())
                .age(userData.getAge())
                .mobileNumber(userData.getMobileNumber())
                .email(userAuthentication.getEmail())
                .role(userAuthentication.getRole().toString())
                .jwtToken(generatedJwtToken)
                .build();
    }
}
