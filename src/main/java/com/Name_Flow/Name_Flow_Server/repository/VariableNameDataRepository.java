package com.Name_Flow.Name_Flow_Server.repository;

import com.Name_Flow.Name_Flow_Server.entity.VariableNameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariableNameDataRepository extends JpaRepository<VariableNameData, Integer> {


}
