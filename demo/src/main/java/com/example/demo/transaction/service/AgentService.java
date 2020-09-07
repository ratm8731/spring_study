package com.example.demo.transaction.service;

import com.example.demo.transaction.entity.AgentEntity;

import java.util.List;

public interface AgentService {
    List<AgentEntity> findAll();
//    List<AgentEntity> findAllWithSoftware();
//    AgentEntity findByid(Long id);
    void save(AgentEntity agent);
//    void delete(AgentEntity agent);
//    void setAdminMessage(AdminSendMessageEntity adminSendMessageEntity);
//    List<AgentEntity> fineAllByNativeQuery();
//    AgentEntity findByCriteriaQuery(String agentname);
//    List<AgentSummary> getAgentSummary();
//    List getAgentSummaryObject();
//
    AgentEntity findByName(String name);
    Long countAll();

    long count();
//    List<AgentEntity> findByLevel(int level);
//    List<AgentEntity> findByDepartment(String department);


}
