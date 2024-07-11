package com.Name_Flow.Name_Flow_Server.repository;

import com.Name_Flow.Name_Flow_Server.entity.UserAccessRelationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccessRelationDataRepository extends JpaRepository<UserAccessRelationData, Long> {

    List<UserAccessRelationData> findAllByUserId(Long user_id);

    void deleteByUserIdAndAccessProjectId(Long user_id,Long accessProjectId);

}
