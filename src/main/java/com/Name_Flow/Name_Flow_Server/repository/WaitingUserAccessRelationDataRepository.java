package com.Name_Flow.Name_Flow_Server.repository;

import com.Name_Flow.Name_Flow_Server.entity.WaitingUserAccessRelationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitingUserAccessRelationDataRepository extends JpaRepository<WaitingUserAccessRelationData,Long> {

    WaitingUserAccessRelationData findByUserIdAndAccessUserIdAndAccessProjectId(
            Long usedId,
            Long accessUserId,
            Long projectId
    );

}
