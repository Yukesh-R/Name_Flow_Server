package com.Name_Flow.Name_Flow_Server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="variable_name_data")
@Data
public class VariableNameData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "variable_name")
    private String variableName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "variable_type")
    private String variableType;

    @Column(name = "description")
    private String description;

}
