package com.example.demo.transaction.repository;

import com.example.demo.transaction.entity.AgentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AgentCrudRepository extends CrudRepository<AgentEntity, Long> {
    AgentEntity findByName(String name);
    @Query("select count(s) from AgentEntity s")
    Long countAllAgents();
}
