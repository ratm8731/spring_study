package com.example.demo.transaction;

import com.example.demo.transaction.entity.AgentEntity;
import com.example.demo.transaction.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication(scanBasePackages = "com.example.demo.transaction.service")
public class JTAApplication implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(JTAApplication.class);

    public static void main(String... args) throws Exception{
        ConfigurableApplicationContext ctx = SpringApplication.run(JTAApplication.class, args);
        System.in.read();
        ctx.close();
    }

    @Autowired
    AgentService agentService;


    @Override
    public void run(String... args) throws Exception {
        AgentEntity agentEntity = new AgentEntity("test4");
        agentEntity.setLevel(2);
        agentEntity.setDepartment("서버셀");
        agentEntity.setCreateDate(new Date());

        agentService.save(agentEntity);

        long count = agentService.count();
        if (count == 1) {
            logger.info("Agent가 성공적으로 저장되었습니다.");
        } else {
            logger.error("Agent 저장에 실패했습니다.");
        }

        try {
            agentService.save(null);
        } catch (Exception e){
            logger.error(e.getMessage() + " Final Count : " + agentService.count());
        }
    }
}
