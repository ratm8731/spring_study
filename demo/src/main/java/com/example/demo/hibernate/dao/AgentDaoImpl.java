package com.example.demo.hibernate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.hibernate.entity.AdminSendMessageEntity;
import com.example.demo.hibernate.entity.AgentEntity;

@Transactional
@Repository("agentDao")
public class AgentDaoImpl implements AgentDao {
	
	private static final Log logger = LogFactory.getLog(AgentDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
	
	@Override
	@Transactional(readOnly = true)
	public List<AgentEntity> findAll() {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from AgentEntity a").list();
	}

	@Override
	public List<AgentEntity> findAllWithSoftware() {
		return getCurrentSession().getNamedQuery("Agent.findAllWithSoftware").list();
	}

	@Override
	public AgentEntity findByid(Long id) {
		return (AgentEntity) getCurrentSession().getNamedQuery("Agent.findId").setParameter("id", id).uniqueResult();
	}

	@Override
	public void save(AgentEntity agent) {
		getCurrentSession().saveOrUpdate(agent);
		logger.info("Agent 레코드 등록 . ID : " + agent.getId());
	}

	@Override
	public void delete(AgentEntity agent) {
		getCurrentSession().delete(agent);
		logger.info("Agent 레코드 삭제 . ID : " + agent.getId());
		
	}
	
	public void setAdminMessage(AdminSendMessageEntity adminSendMessageEntity) {
		getCurrentSession().save(adminSendMessageEntity);
	}
		
}
