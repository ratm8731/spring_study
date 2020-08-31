package com.example.demo.jpa.repository;

import com.example.demo.jpa.entity.AgentAuditEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AgentAuditRepository extends CrudRepository<AgentAuditEntity, Long> {
    List<AgentAuditEntity> findAll();
    Optional<AgentAuditEntity> findById(Long id);
    AgentAuditEntity save(AgentAuditEntity agentAuditEntity);
}
