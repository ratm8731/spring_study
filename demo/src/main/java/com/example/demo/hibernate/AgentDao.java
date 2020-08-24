package com.example.demo.hibernate;

import java.util.List;


public interface AgentDao  {
	List<AgentEntity> findAll();
	List<AgentEntity> findAllWithSoftware();
	AgentEntity findByid(Long id);
	void save(AgentEntity agent);
	void delete(AgentEntity agent);
	void setAdminMessage(AdminSendMessageEntity adminSendMessageEntity);
}
