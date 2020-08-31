package com.example.demo.jpa.service.impl;

import com.example.demo.jpa.entity.SoftwareEntity;
import com.example.demo.jpa.repository.AgentJpaRepository;
import com.example.demo.jpa.service.SoftwareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("jpaSoftwareServiceImpl")
@Transactional
public class SoftwareServiceImpl implements SoftwareService {
    private static Logger logger = LoggerFactory.getLogger(SoftwareServiceImpl.class);

    @Autowired
    private AgentJpaRepository agentJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SoftwareEntity> findAgentIdBySoftWare(Long id) {
        return agentJpaRepository.findAgentIdBySoftWare(id);
    }

    @Override
    public List<SoftwareEntity> findBySoftWareName(String name) {
        return agentJpaRepository.findBySoftWareName(name);
    }
}
