package com.Name_Flow.Name_Flow_Server.repository;

import com.Name_Flow.Name_Flow_Server.entity.WaitingUserSharedRelationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingUserSharedRelationDataRepository extends JpaRepository<WaitingUserSharedRelationData,Long> {

    WaitingUserSharedRelationData findBySharedProjectIdAndSharedUserIdAndUserId(
            Long sharedProjectId,
            Long sharedUserId,
            Long userId
    );

}
