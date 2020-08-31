package com.example.demo.jpa.service.impl;

import com.example.demo.jpa.entity.AgentAuditEntity;
import com.example.demo.jpa.repository.AgentAuditRepository;
import com.example.demo.jpa.service.AgentAuditService;
import org.assertj.core.util.Lists;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Service("AgentAuditService")
@Transactional
public class AgentAuditServiceImpl implements AgentAuditService {

    @Autowired
    AgentAuditRepository agentAuditRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<AgentAuditEntity> findAll() {
        return Lists.newArrayList(agentAuditRepository.findAll());
    }

    @Override
    public AgentAuditEntity findById(Long id) {
        return agentAuditRepository.findById(id).get();
    }

    @Override
    public AgentAuditEntity save(AgentAuditEntity agentAuditEntity) {
        return agentAuditRepository.save(agentAuditEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public AgentAuditEntity findAuditByRevision(Long id, int revision) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        return auditReader.find(AgentAuditEntity.class, id, revision);
    }
}
