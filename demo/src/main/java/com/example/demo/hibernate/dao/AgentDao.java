package com.example.demo.hibernate.dao;

import java.util.List;

import com.example.demo.hibernate.entity.AdminSendMessageEntity;
import com.example.demo.hibernate.entity.AgentEntity;


public interface AgentDao  {
	List<AgentEntity> findAll();
	List<AgentEntity> findAllWithSoftware();
	AgentEntity findByid(Long id);
	void save(AgentEntity agent);
	void delete(AgentEntity agent);
	void setAdminMessage(AdminSendMessageEntity adminSendMessageEntity);
}
