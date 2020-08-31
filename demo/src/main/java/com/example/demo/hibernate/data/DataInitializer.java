package com.example.demo.hibernate.data;

import com.example.demo.hibernate.dao.AgentDao;
import com.example.demo.hibernate.entity.AdminSendMessageEntity;
import com.example.demo.hibernate.entity.AgentEntity;
import com.example.demo.hibernate.entity.SoftwareEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

//@Service
public class DataInitializer {
    private Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    AgentDao agentDao;

    @PostConstruct
    public void initDB() {
        logger.info("Start Database initialization");

        AgentEntity agetEntity = new AgentEntity("test1");
        AgentEntity agetEntity2 = new AgentEntity("test2");
        AgentEntity agetEntity3 = new AgentEntity("test3");

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
        logger.info("Database initialization finish");
    }
}
