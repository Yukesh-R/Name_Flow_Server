package com.Name_Flow.Name_Flow_Server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Table(name="user_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="gender")
    private String gender;

    @Column(name="age")
    private Integer age;

    @Column(name="mobile_number")
    private String mobileNumber;

}
