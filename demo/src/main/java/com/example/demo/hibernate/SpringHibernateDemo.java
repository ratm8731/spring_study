package com.example.demo.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;

import com.example.demo.config.HibernateConfig;
import com.example.demo.hibernate.dao.AgentDao;
import com.example.demo.hibernate.entity.AdminSendMessageEntity;
import com.example.demo.hibernate.entity.AgentEntity;
import com.example.demo.hibernate.entity.SoftwareEntity;

@ComponentScan(basePackages= {"com.example.demo.hibernate"}) 
public class SpringHibernateDemo {
	private static Logger logger = LoggerFactory.getLogger(SpringHibernateDemo.class);
	private static AgentDao agentDao;
	private static AgentEntity agetEntity;
	private static AgentEntity agetEntity2;
	private static AgentEntity agetEntity3;
	
	public static void main(String... args) {
		GenericApplicationContext ctx = new AnnotationConfigApplicationContext(HibernateConfig.class);
		agentDao = ctx.getBean(AgentDao.class);
		
		// 데이터 생성
		createAgentData();
		
		// 데이터 조회
		List<AgentEntity> agents = agentDao.findAll();
		listAgentsWithMessage(agents);
		
		List<AgentEntity> agents2 = agentDao.findAllWithSoftware();
		listAgentsWithMessage(agents2);
		
		AgentEntity agent = agentDao.findByid(agetEntity3.getId());
		logger.info("에이전트 이름 : "+agent.getName());
		logger.info("에이전트 - 소프트웨어 : "+agent.getSoftware().toString());
		logger.info("에이전트 - 받은 메시지 : "+agent.getAdminSendMessage().toString());
		
		// 데이터 수정
		SoftwareEntity software = agent.getSoftware().stream().filter(a -> a.getName().equals("카카오")).findFirst().get();
		agent.removeSoftware(software);
		agent.addSoftware(new SoftwareEntity("알약"));
		agent.setName("테스트1");
		agent.setDepartment("서버셀");
		agentDao.save(agent);
		listAgentsWithMessage(agentDao.findAllWithSoftware());
		
		// 데이터 삭제
		AgentEntity agent2 = agentDao.findByid(agetEntity2.getId());
		agentDao.delete(agent2);
		listAgentsWithMessage(agentDao.findAllWithSoftware());
	}
	
	private static void createAgentData() {
		agetEntity = new AgentEntity("test1");
		agetEntity2 = new AgentEntity("test2");
		agetEntity3 = new AgentEntity("test3");
		
		agetEntity2.addSoftware(new SoftwareEntity("윈도우"));
		agetEntity2.addSoftware(new SoftwareEntity("카카오"));
		
		agetEntity3.addSoftware(new SoftwareEntity("윈도우"));
		agetEntity3.addSoftware(new SoftwareEntity("카카오"));
		
		
		
		
		agetEntity3.addSoftware(new SoftwareEntity("카카오"));
		
		agentDao.save(agetEntity);
		agentDao.save(agetEntity2);
		agentDao.save(agetEntity3);
		
		Set<AdminSendMessageEntity> adminSendMessage = new HashSet<>();
		AdminSendMessageEntity adminSendMessageEntity = new AdminSendMessageEntity();
		adminSendMessageEntity.setId(1L);
		adminSendMessageEntity.setMessage("메시지");
		
		adminSendMessage.add(adminSendMessageEntity);
		
		Set<AgentEntity> sendAgents = new HashSet<>();
		sendAgents.add(agetEntity2);
		sendAgents.add(agetEntity3);
		
		adminSendMessageEntity.setAgents(sendAgents);
		agentDao.setAdminMessage(adminSendMessageEntity);
	}
	
	private static void listAgentsWithMessage(List<AgentEntity> agents) {
		logger.info("------ 에이전트 목록 ------");
		agents.forEach(a->{
			logger.info(a.toString());
			if(a.getSoftware() != null) {
				a.getSoftware().forEach(s->{
					logger.info(s.toString());
				});
			}
			if(a.getAdminSendMessage() != null) {
				a.getAdminSendMessage().forEach(m->{
					logger.info(m.toString());
				});
			}
		});
		logger.info("------ 에이전트 목록 ------");
		
	}
	
	
	
}
