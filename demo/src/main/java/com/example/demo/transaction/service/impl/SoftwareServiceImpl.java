package com.example.demo.transaction.service.impl;

import com.example.demo.transaction.entity.SoftwareEntity;
import com.example.demo.transaction.exception.AsyncXAResourceException;
import com.example.demo.transaction.repository.SoftwareRepository;
import com.example.demo.transaction.service.SoftwareService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("softwareService")
@Transactional
public class SoftwareServiceImpl implements SoftwareService {

    private SoftwareRepository softwareRepository;
    private JmsTemplate jmsTemplate;

    public SoftwareServiceImpl(SoftwareRepository softwareRepository, JmsTemplate jmsTemplate) {
        this.softwareRepository = softwareRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public List<SoftwareEntity> findAll() {
        return null;
    }

    @Override
    public SoftwareEntity save(SoftwareEntity softwareEntity) throws AsyncXAResourceException {
        jmsTemplate.convertAndSend("software","방금 저장 됨"+softwareEntity);
        if (softwareEntity == null) {
            throw new AsyncXAResourceException("잘못 된 상황의 시뮬레이션");
        }
        softwareRepository.save(softwareEntity);
        return softwareEntity;
    }

    @Override
    public long count() {
        return softwareRepository.count();
    }
}
