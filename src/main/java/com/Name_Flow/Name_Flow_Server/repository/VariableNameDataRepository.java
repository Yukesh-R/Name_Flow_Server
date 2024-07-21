package com.Name_Flow.Name_Flow_Server.repository;

import com.Name_Flow.Name_Flow_Server.entity.VariableNameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariableNameDataRepository extends JpaRepository<VariableNameData, Integer> {

    VariableNameData findById(Long id);

    List<VariableNameData> findAllByProjectId(Long projectId);

}
