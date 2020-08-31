package com.example.demo.jpa.service;

import com.example.demo.jpa.entity.AgentAuditEntity;

import java.util.List;

public interface AgentAuditService {
    List<AgentAuditEntity> findAll();
    AgentAuditEntity findById(Long id);
    AgentAuditEntity save(AgentAuditEntity agentAuditEntity);
    AgentAuditEntity findAuditByRevision(Long id, int revision);
}
