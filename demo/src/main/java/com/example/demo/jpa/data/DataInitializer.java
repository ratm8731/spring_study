package com.example.demo.jpa.data;

import com.example.demo.jpa.entity.AdminSendMessageEntity;
import com.example.demo.jpa.entity.AgentEntity;
import com.example.demo.jpa.entity.SoftwareEntity;
import com.example.demo.jpa.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class DataInitializer {
    private Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    AgentService agentService;

    @PostConstruct
    public void initDB() throws ParseException {
        logger.info("Start Database initialization");

        Date date = new Date();
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = day.parse("2020-08-01 00:00:00");
        Date date2 =day.parse("2020-04-01 00:00:00");

        AgentEntity agetEntity = new AgentEntity("test1");
        AgentEntity agetEntity2 = new AgentEntity("test2");
        AgentEntity agetEntity3 = new AgentEntity("test3");
        agetEntity.setDepartment("서버셀");
        agetEntity.setLevel(1);
        agetEntity.setCreateDate(date);
        agetEntity2.setDepartment("클라이언트셀");
        agetEntity2.setLevel(2);
        agetEntity2.setCreateDate(date1);
        agetEntity3.setDepartment("기획팀");
        agetEntity3.setLevel(2);
        agetEntity3.setCreateDate(date);

        agetEntity2.addSoftware(new SoftwareEntity("카카오톡",date2));
        agetEntity2.addSoftware(new SoftwareEntity("곰플레이어",date1));
        agetEntity3.addSoftware(new SoftwareEntity("카카오톡",date));
        agetEntity3.addSoftware(new SoftwareEntity("곰플레이어",date1));
        agetEntity3.addSoftware(new SoftwareEntity("윈도우",date2));

        agentService.save(agetEntity);
        agentService.save(agetEntity2);
        agentService.save(agetEntity3);

        Set<AdminSendMessageEntity> adminSendMessage = new HashSet<>();
        AdminSendMessageEntity adminSendMessageEntity = new AdminSendMessageEntity();
        adminSendMessageEntity.setId(1L);
        adminSendMessageEntity.setMessage("메시지");

        adminSendMessage.add(adminSendMessageEntity);

        Set<AgentEntity> sendAgents = new HashSet<>();
        sendAgents.add(agetEntity2);
        sendAgents.add(agetEntity3);

        adminSendMessageEntity.setAgents(sendAgents);
        agentService.setAdminMessage(adminSendMessageEntity);
        logger.info("Database initialization finish");
    }
}
