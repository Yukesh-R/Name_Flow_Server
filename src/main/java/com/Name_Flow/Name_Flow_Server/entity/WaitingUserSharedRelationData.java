package com.Name_Flow.Name_Flow_Server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="waiting_user_shared_relation_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WaitingUserSharedRelationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_id")
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="shared_user_id")
    private Long sharedUserId;

    @Column(name="shared_project_id")
    private Long sharedProjectId;

}
