package com.example.demo.jpa.service.impl;

import com.example.demo.jpa.entity.*;
import com.example.demo.jpa.repository.AgentCrudRepository;
import com.example.demo.jpa.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Service("jpaAgentServiceImpl")
@Transactional
public class AgentServiceImpl implements AgentService {
    final static String ALL_AGENT_NATIVE_QUERY = "select id, name, department, level, version, create_date from agent";
    private static Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    private AgentCrudRepository agentRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<AgentEntity> findAll() {
        return entityManager.createNamedQuery(AgentEntity.FIND_ALL, AgentEntity.class).getResultList();
    }

    @Override
    public List<AgentEntity> findAllWithSoftware() {
        return entityManager.createNamedQuery(AgentEntity.FIND_ALL_WITH_SOFTWARE, AgentEntity.class).getResultList();
    }

    @Override
    public AgentEntity findByid(Long id) {
        TypedQuery<AgentEntity> query = entityManager.createNamedQuery(AgentEntity.FIND_AGENT_BY_ID, AgentEntity.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }

    @Override
    public void save(AgentEntity agent) {
        if (agent.getId() == null) {
            entityManager.persist(agent);
            logger.info("에이전트 등록");
        } else {
            entityManager.merge(agent);
            logger.info("에이전트 수정");
        }

    }

    @Override
    public void delete(AgentEntity agent) {
        AgentEntity mergedContact = entityManager.merge(agent);
        entityManager.remove(mergedContact);
        logger.info("에이전트 ( " +agent.getId()+ " ) 를 삭제했습니다.");
    }

    @Override
    public void setAdminMessage(AdminSendMessageEntity adminSendMessageEntity) {

        entityManager.merge(adminSendMessageEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AgentEntity> fineAllByNativeQuery() {
        return entityManager.createNativeQuery(ALL_AGENT_NATIVE_QUERY, "agentResult").getResultList();
    }

    @Override
    public AgentEntity findByCriteriaQuery(String agentname) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AgentEntity> criteriaQuery = cb.createQuery(AgentEntity.class);
        Root<AgentEntity> agentEntityRoot = criteriaQuery.from(AgentEntity.class);
        agentEntityRoot.fetch(AgentEntity_.software, JoinType.LEFT);
        agentEntityRoot.fetch(AgentEntity_.adminSendMessage, JoinType.LEFT);

        criteriaQuery.select(agentEntityRoot).distinct(true);

        Predicate criteria = cb.conjunction();

        if (agentname != null) {
            Predicate p = cb.equal(agentEntityRoot.get(AgentEntity_.name),agentname);
            criteria = cb.and(criteria, p);
        }

        criteriaQuery.where(criteria);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AgentSummary> getAgentSummary() {
        List<AgentSummary> agentSummaryList = entityManager.createQuery(
                "select new com.example.demo.jpa.entity.AgentSummary("+
                "agent.name, agent.software.size, software.name) from AgentEntity agent " +
                "left join agent.software software " +
                "where software.insertDate = " +
                "(select max(software2.insertDate) from SoftwareEntity software2 where software2.agent.id = agent.id)", AgentSummary.class
        ).getResultList();
        return agentSummaryList;
    }

    @Override
    public List getAgentSummaryObject() {
        List result = entityManager.createQuery(
                "select agent.name, agent.software.size, software.name from AgentEntity agent " +
                        "left join agent.software software " +
                        "where software.insertDate = " +
                        "(select max(software2.insertDate) from SoftwareEntity software2 where software2.agent.id = agent.id)")
                .getResultList();
        return result;
    }

    @Override
    public List<AgentEntity> findByLevel(int level) {
        return agentRepository.findByLevel(level);
    }

    @Override
    public List<AgentEntity> findByDepartment(String department) {
        return agentRepository.findByDepartment(department);
    }

    @Override
    public AgentEntity findByName(String name) {
        return agentRepository.findByName(name);
    }


}
