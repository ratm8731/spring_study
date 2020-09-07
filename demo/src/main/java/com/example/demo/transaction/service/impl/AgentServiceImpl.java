package com.example.demo.transaction.service.impl;

import com.example.demo.transaction.entity.AgentEntity;
import com.example.demo.transaction.repository.AgentCrudRepository;
import com.example.demo.transaction.service.AgentService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service("agentService")
@Transactional
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentCrudRepository agentCrudRepository;

//    @Autowired
//    public void setAgentCrudRepository(AgentCrudRepository agentCrudRepository) {
//        this.agentCrudRepository = agentCrudRepository;
//    }

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<AgentEntity> findAll() {
        return Lists.newArrayList(agentCrudRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public AgentEntity findByName(String name) {
        return agentCrudRepository.findByName(name);
    }

    @Override
    public Long countAll() {
        return transactionTemplate.execute(new TransactionCallback<Long>() {
               @Override
               public Long doInTransaction(TransactionStatus status) {
                   return entityManager.createNamedQuery(AgentEntity.COUNT_ALL,Long.class).getSingleResult();
               }
           }

        );
    }

    @Override
    public long count() {
        return agentCrudRepository.count();
    }

    @Override
    public void save(AgentEntity agent) {
        agentCrudRepository.save(agent);
    }


}
