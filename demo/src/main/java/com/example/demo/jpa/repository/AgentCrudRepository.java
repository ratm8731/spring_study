package com.example.demo.jpa.repository;

import com.example.demo.jpa.entity.AgentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AgentCrudRepository extends CrudRepository<AgentEntity, Long> {
    AgentEntity findByName(String name);
    List<AgentEntity> findByLevel(int level);
    List<AgentEntity> findByDepartment(String department);
}
