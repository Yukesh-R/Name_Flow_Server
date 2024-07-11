package com.Name_Flow.Name_Flow_Server.repository;

import com.Name_Flow.Name_Flow_Server.entity.ProjectData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDataRepository extends JpaRepository<ProjectData, Long> {

    boolean existsByProjectName(String projectName);

    List<ProjectData> findAllByUserId(Long user_id);

}
