package com.Name_Flow.Name_Flow_Server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="waiting_user_access_relation_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WaitingUserAccessRelationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_id")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="access_user_id")
    private Long accessUserId;

    @Column(name="access_project_id")
    private Long accessProjectId;

}
