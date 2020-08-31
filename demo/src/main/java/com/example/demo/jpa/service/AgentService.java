package com.example.demo.jpa.service;

import com.example.demo.jpa.entity.AdminSendMessageEntity;
import com.example.demo.jpa.entity.AgentEntity;
import com.example.demo.jpa.entity.AgentSummary;
import com.example.demo.jpa.entity.SoftwareEntity;

import java.util.List;

public interface AgentService {
    List<AgentEntity> findAll();
    List<AgentEntity> findAllWithSoftware();
    AgentEntity findByid(Long id);
    void save(AgentEntity agent);
    void delete(AgentEntity agent);
    void setAdminMessage(AdminSendMessageEntity adminSendMessageEntity);
    List<AgentEntity> fineAllByNativeQuery();
    AgentEntity findByCriteriaQuery(String agentname);
    List<AgentSummary> getAgentSummary();
    List getAgentSummaryObject();

    AgentEntity findByName(String name);
    List<AgentEntity> findByLevel(int level);
    List<AgentEntity> findByDepartment(String department);


}
