package com.Name_Flow.Name_Flow_Server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="project_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProjectData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name="user_id")
    private String userId;

    @Column(name="project_name")
    private String projectName;

    @Column(name="project_description")
    private String projectDescription;

    @Column(name="tech_stack_description")
    private String techStackDescription;

    @Column(name="project_contributors")
    private String projectContributors;

}
