package com.example.demo.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;

import com.example.demo.hibernate.config.HibernateConfig;
import com.example.demo.hibernate.dao.AgentDao;
import com.example.demo.hibernate.entity.AdminSendMessageEntity;
import com.example.demo.hibernate.entity.AgentEntity;
import com.example.demo.hibernate.entity.SoftwareEntity;

@ComponentScan(basePackages= {"com.example.demo.hibernate"}) 
@SpringBootApplication
public class SpringHibernateDemo {
	private static Logger logger = LoggerFactory.getLogger(SpringHibernateDemo.class);
	private static AgentDao agentDao;
	
	public static void main(String... args) {
		GenericApplicationContext ctx = new AnnotationConfigApplicationContext(HibernateConfig.class);
		agentDao = ctx.getBean(AgentDao.class);
		
		// 데이터 생성
//		createAgentData();
		
		// 데이터 조회
		List<AgentEntity> agents = agentDao.findAll();
		listAgentsWithMessage(agents);
		
		List<AgentEntity> agents2 = agentDao.findAllWithSoftware();
		listAgentsWithMessage(agents2);
		
		AgentEntity agent = agentDao.findByid(agents.get(2).getId());
//		logger.info("에이전트 이름 : "+agent.getName());
//		logger.info("에이전트 - 소프트웨어 : "+agent.getSoftware().toString());
//		logger.info("에이전트 - 받은 메시지 : "+agent.getAdminSendMessage().toString());
		
		// 데이터 수정
		SoftwareEntity software = agent.getSoftware().stream().filter(a -> a.getName().equals("카카오")).findFirst().get();
		agent.removeSoftware(software);
		agent.addSoftware(new SoftwareEntity("알약"));
		agent.setName("테스트1");
		agent.setDepartment("서버셀");
		agentDao.save(agent);
		listAgentsWithMessage(agentDao.findAllWithSoftware());
		
		// 데이터 삭제
		AgentEntity agent2 = agentDao.findByid(agents.get(1).getId());
		agentDao.delete(agent2);
		listAgentsWithMessage(agentDao.findAllWithSoftware());
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
