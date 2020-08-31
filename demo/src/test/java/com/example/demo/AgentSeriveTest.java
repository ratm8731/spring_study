package com.example.demo;

import com.example.demo.jpa.config.JPAConfig;
import com.example.demo.jpa.entity.AgentAuditEntity;
import com.example.demo.jpa.entity.AgentEntity;
import com.example.demo.jpa.entity.AgentSummary;
import com.example.demo.jpa.entity.SoftwareEntity;
import com.example.demo.jpa.service.AgentAuditService;
import com.example.demo.jpa.service.AgentService;
import com.example.demo.jpa.service.SoftwareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AgentSeriveTest {
    private static Logger logger = LoggerFactory.getLogger(AgentSeriveTest.class);

    private GenericApplicationContext ctx;
    private AgentService agentService;
    private SoftwareService softwareService;
    private AgentAuditService agentAuditService;

    @BeforeEach
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(JPAConfig.class);
        agentService = ctx.getBean(AgentService.class);
        softwareService = ctx.getBean(SoftwareService.class);
        agentAuditService = ctx.getBean(AgentAuditService.class);
        assertNotNull(agentService);
    }

    @Test
    public void 전체에이전트목록() {
        List<AgentEntity> agentEntityList = agentService.findAll();
        assertEquals(3, agentEntityList.size());
        listAgents(agentEntityList);
    }

    @Test
    public void 특정에이전트가져오기() {
        AgentEntity agentEntity = agentService.findByid(3L);
        assertEquals("test3", agentEntity.getName());

    }

    @Test
    public void 명시되지않은타입의결과쿼리(){
        List agentSummaries = agentService.getAgentSummaryObject();

        assertEquals(2, agentSummaries.size());

        for (Iterator i = agentSummaries.iterator(); i.hasNext(); ) {
            Object[] values = (Object[]) i.next();
            String agentName = (String) values[0];
            int size = (int) values[1];
            String softwareName = (String) values[2];
            logger.info("agent name : " + agentName);
            logger.info("software size : " + size);
            logger.info("last install software name : " + softwareName);
        }
    }

    @Test
    public void 에이전트통계데이터가져오기(){
        List<AgentSummary> agentSummaries = agentService.getAgentSummary();
        assertEquals(2, agentSummaries.size());
        listAgentsSummary(agentSummaries);
    }

    @Test
    public void 에이전트등록(){
        AgentEntity agetEntity = new AgentEntity("test4");
        agetEntity.setDepartment("서버셀");
        agetEntity.setLevel(2);
        agetEntity.setCreateDate(new Date());

        agentService.save(agetEntity);
        assertNotNull(agetEntity.getId());
        List<AgentEntity> agentEntityList = agentService.findAll();
        assertEquals(4,agentEntityList.size());
    }

    @Test
    public void 에이전트수정(){

        List<AgentEntity> agentEntityList = agentService.findAll();
        AgentEntity agentEntity = agentEntityList.get(0);
        assertNotNull(agentEntity);
        assertEquals(0, agentEntity.getSoftware().size());
        agentEntity.addSoftware(new SoftwareEntity("윈도우",new Date()));
        agentService.save(agentEntity);
        assertEquals(1, agentEntity.getSoftware().size());

    }

    @Test
    public void 에이전트삭제(){

        List<AgentEntity> agentEntityList = agentService.findAll();
        AgentEntity agentEntity = agentEntityList.get(0);
        assertNotNull(agentEntity);
        agentService.delete(agentEntity);
        assertNotNull(agentEntity);

    }

    @Test
    public void 크리테이라에이전트조회(){

        AgentEntity agentEntity = agentService.findByCriteriaQuery("test2");
        assertNotNull(agentEntity);
        logger.info("----- 에이전트 정보 -----");
        logger.info(agentEntity.toString());
        logger.info("----- 에이전트 정보 -----");
        logger.info("----- 소프트웨어 정보 -----");
        logger.info(agentEntity.getSoftware().toString());
        logger.info("----- 소프트웨어 정보 -----");

    }

    @Test
    public void 레포지토리를통한에이전트레벨조회(){

        List<AgentEntity> agentEntityList = agentService.findByLevel(2);
        assertTrue(agentEntityList.size() > 0);
        assertEquals(2,agentEntityList.size());
        listAgents(agentEntityList);





    }

    @Test
    public void 레포지토리를통한에이전트부서조회(){

        List<AgentEntity> agentEntityList = agentService.findByDepartment("서버셀");
        assertTrue(agentEntityList.size() > 0);
        assertEquals(1,agentEntityList.size());
        listAgents(agentEntityList);


    }

    @Test
    public void 소프트웨어에이전트조회(){
        AgentEntity agentEntity = agentService.findByName("test2");
        List<SoftwareEntity> softwareEntities = softwareService.findAgentIdBySoftWare(agentEntity.getId());

        assertTrue(softwareEntities.size() > 0);
        assertEquals(2,softwareEntities.size());



    }

    @Test
    public void 소프트웨어이름조회(){

        List<SoftwareEntity> softwareEntities = softwareService.findBySoftWareName("카카오톡");
        assertTrue(softwareEntities.size() > 0);
        assertEquals(2,softwareEntities.size());


    }

    @Test
    public void 버전관리(){

        List<AgentAuditEntity> agentAuditEntities = agentAuditService.findAll();
        listAgentAudits(agentAuditEntities);
        logger.info("----- 에이전트 등록 -----");
        AgentAuditEntity agentAuditEntity = new AgentAuditEntity();
        agentAuditEntity.setName("test1");
        agentAuditEntity.setDepartment("서버셀");
        agentAuditEntity.setLevel(1);
        agentAuditService.save(agentAuditEntity);

        agentAuditEntities = agentAuditService.findAll();
        listAgentAudits(agentAuditEntities);

        agentAuditEntity = agentAuditService.findById(1L);
        logger.info("ID가 1인 에이전트 : " + agentAuditEntity);


        logger.info("----- 에이전트 정보 수정 -----");
        agentAuditEntity.setLevel(2);
        agentAuditEntity.setName("test3");
        agentAuditService.save(agentAuditEntity);

        agentAuditEntities = agentAuditService.findAll();
        listAgentAudits(agentAuditEntities);
        
        AgentAuditEntity oldAgent = agentAuditService.findAuditByRevision(1L, 1);
        logger.info("ID가 1이고 개정번호 1인 이전 에이전트 정보 : " + agentAuditEntity);

        oldAgent = agentAuditService.findAuditByRevision(1L, 1);
        logger.info("ID가 1이고 개정번호 2인 이전 에이전트 정보 : " + agentAuditEntity);





    }

    private static void listAgents(List<AgentEntity> agentEntities) {
        logger.info("----- 에이전트 목록 -----");
        agentEntities.forEach(s->logger.info(s.toString()));
    }

    private static void listAgentsSummary(List<AgentSummary> agentSummaries) {
        logger.info("----- 에이전트 목록 -----");
        agentSummaries.forEach(s->logger.info(s.toString()));
    }

    private static void listAgentAudits(List<AgentAuditEntity> agentAuditEntities) {

    }
}
