package com.Name_Flow.Name_Flow_Server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDetailsDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
    private String mobileNumber;
}
