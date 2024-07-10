package com.Name_Flow.Name_Flow_Server.dto;

import com.Name_Flow.Name_Flow_Server.entity.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegistrationRequestDTO {

    private String firstName;

    private String lastName;

    private String gender;

    private Integer age;

    private String mobileNumber;

    private String email;

    private String password;

    private String role;

    private String activationCode;

}
