package com.example.demo.transaction;

import com.example.demo.transaction.entity.AgentEntity;
import com.example.demo.transaction.config.ServiceConfig;
import com.example.demo.transaction.config.DataJPAConfig;
import com.example.demo.transaction.service.AgentService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

public class SpringTransactionDemo {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceConfig.class, DataJPAConfig.class);

        AgentService agentService = ctx.getBean(AgentService.class);


        List<AgentEntity> agentEntityList = agentService.findAll();
        agentEntityList.forEach(s-> System.out.println(s));

        AgentEntity agentEntity = agentService.findByName("test1");
        agentEntity.setDepartment("플랫폼셀");
        agentEntity.setLevel(2);
        agentService.save(agentEntity);
        System.out.println("에이전트가 성공적으로 저장됐습니다 : "+agentEntity);
        System.out.println("에이전트 숫자 : "+agentService.countAll());
        ctx.close();
    }
}
